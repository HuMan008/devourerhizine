/*
 * ******************************************************
 *  * Copyright (C) 2018 cluries  <cluries#me.com>
 *  *
 *  * This file is part of Devourer.
 *  *
 *  * Devourer can not be copied and/or distributed without the express
 *  * permission of cluries
 *  ******************************************************
 */

package com.petroun.devourerhizine.service.impl.cnpc;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.provider.bill.BillFlow;
import com.petroun.devourerhizine.classes.BitMask;
import com.petroun.devourerhizine.classes.cnpc.CnpcOrderHelper;
import com.petroun.devourerhizine.classes.rabbitmq.MQDefiner;
import com.petroun.devourerhizine.classes.rabbitmq.MQPublisher;
import com.petroun.devourerhizine.classes.tools.RedisLocker;
import com.petroun.devourerhizine.config.CnpcConfig;
import com.petroun.devourerhizine.enums.OrderError;
import com.petroun.devourerhizine.model.CnpcRegainBit;
import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.model.entity.CnpcOrderExample;
import com.petroun.devourerhizine.model.entity.CnpcOrderInvokeLog;
import com.petroun.devourerhizine.model.enums.CnpcOrderInvokeLogType;
import com.petroun.devourerhizine.model.enums.CnpcOrderState;
import com.petroun.devourerhizine.model.mapper.CnpcOrderInvokeLogMapper;
import com.petroun.devourerhizine.model.mapper.CnpcOrderMapper;
import com.petroun.devourerhizine.provider.cnpc.*;
import com.petroun.devourerhizine.provider.petroun.FlowHelper;
import com.petroun.devourerhizine.provider.petroun.Rhizine;
import com.petroun.devourerhizine.service.OptionService;
import com.petroun.devourerhizine.service.cnpc.CnpcOrderService;
import com.petroun.devourerhizine.service.cnpc.CnpcRechargeService;
import com.petroun.devourerhizine.web.message.request.sinopec.Recharge;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;


@Service
public class CnpcRechargeServiceImpl implements CnpcRechargeService {

    private static final Logger logger = LoggerFactory.getLogger(CnpcRechargeServiceImpl.class);

    @Autowired
    private CnpcConfig cnpcConfig;

    @Autowired
    private CnpcOrderService orderService;

    @Autowired
    private CnpcOrderMapper cnpcOrderMapper;

    @Autowired
    private CnpcOrderInvokeLogMapper cnpcOrderInvokeLogMapper;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Connection connection;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private static final int MAX_PGTC_THROUGH_COUNT = 120;
    private static final int MAX_PROVIDER_THROUGH_COUNT = 4;

    private static final int MAX_INQUIRE_COUNT = 1024;

    private static final int THROUGH_PTGC_MASK_BIT = 0;
    private static final int THROUGH_PROVIDER_MASK_BIT = 1;


    @Override
    public CnpcOrder recharge(Recharge recharge, Byte apiVersion) {
        CnpcOrder order = orderService.persist(recharge, apiVersion);
        if (!appendThroughQueue(order.getId(), MQPublisher.DelayInterval.IMMEDIATELY)) {
            logger.error("AppendThroughQueueFailed:{}", order.getId());
        }

        return order;
    }

    @Override
    public CnpcOrder inquire(Long id, boolean disableAppendInquireQueue) {
        CnpcOrder order = cnpcOrderMapper.selectByPrimaryKey(id);
        if (order == null) {
            throw new BillException(OrderError.NotExist);
        }

        return inquire(order, disableAppendInquireQueue);
    }

    @Override
    public CnpcOrder inquire(CnpcOrder order, boolean disableAppendInquireQueue) {
        if (!CnpcOrderState.Requested.getCode().equals(order.getState())) {
            logger.error("order:{} state is:{}, pass inquire", order.getId(), order.getState());
            return order;
        }

        if (order.getInquires() > MAX_INQUIRE_COUNT) {
            logger.error("order:{} inquires is:{}, pass inquire", order.getId(), order.getInquires());
            return order;
        }

        CNPC cnpc = new CNPC(order.getChannel());
        cnpc.setCnpcConfig(cnpcConfig);
        CnpcProviderInquireResult inquireResult = cnpc.inquire(order.getId(), order.getChannelOrder(), order.getDirectorCard());

        CnpcOrder ue = new CnpcOrder();
        if (inquireResult.getResult().isPresent()) {
            Integer ueState;
            if (inquireResult.getResult().get()) {
                ueState = CnpcOrderState.Successed.getCode();

                ue.setChannelOrder(inquireResult.getProviderOrder());
                ue.setDirectorOrder(inquireResult.getDirectorOrder());

                order.setChannelOrder(inquireResult.getProviderOrder());
                order.setDirectorOrder(inquireResult.getDirectorOrder());
            } else {
                ueState = CnpcOrderState.Failed.getCode();
                if (inquireResult.getFailureReason() != null) {
                    ue.setFailureReason(inquireResult.getFailureReason());
                    order.setFailureReason(inquireResult.getFailureReason());
                }
            }

            ue.setState(ueState);
            order.setState(ueState);
        }

        int inquireCount = order.getInquires() + 1;
        order.setInquires(inquireCount);
        ue.setInquires(inquireCount);

        CnpcOrderExample example = new CnpcOrderExample();
        CnpcOrderExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(order.getId()).andStateEqualTo(CnpcOrderState.Requested.getCode());

        if (cnpcOrderMapper.updateByExampleSelective(ue, example) < 1) {
            logger.error("Update cnpc order failed:{}", order);
        } else {
            if (CnpcOrderState.Failed.getCode().equals(order.getState())
                    && order.getRegain().equals(0)) {
                ////////appendRegainQueue///////
                if (!appendRegainQueue(order.getId())) {
                    logger.error("Append order:{} to regian queue failed", order.getId());
                }
            }
        }

        boolean requeue = CnpcOrderState.Requested.getCode().equals(order.getState())
                &&
                inquireResult.isNeedRetry() && inquireCount <= MAX_INQUIRE_COUNT;

        if (!disableAppendInquireQueue && requeue && !appendInquireQueue(order)) {
            logger.error("Append order:{} to inquire queue failed", order.getId());
        }

        loggingOperationByCNPCProviderLog(inquireResult.getProviderLog(), order,
                CnpcOrderInvokeLogType.Inquire);

        return order;
    }

    @Override
    public CnpcOrder through(Long id) {
        RedisLocker locker = new RedisLocker(stringRedisTemplate, "through_" + id.toString(), 90);
        if (!locker.trylock()) {
            if (!appendThroughQueue(id, MQPublisher.DelayInterval.M1)) {
                logger.error("append through queue failed: {}", id);
            }
            return null;
        }

        CnpcOrder order = throughWork(id);

        if (order != null) {
            if (CnpcOrderState.Requested.getCode().equals(order.getState())) {
                if (order.getInquires() < MAX_INQUIRE_COUNT) {
                    if (!appendInquireQueue(order)) {
                        logger.error("Append order:{} to inquire queue failed", order.getId());
                    }
                }
            } else if (CnpcOrderState.Failed.getCode().equals(order.getState())) {
                if (!appendRegainQueue(order.getId())) {
                    logger.error("Append order:{} to regain queue failed", order.getId());
                }
            }
        }

        locker.unlock();

        return order;
    }

    @Override
    public CnpcOrder regain(Long id) {
        RedisLocker locker = new RedisLocker(stringRedisTemplate, "regain_" + id.toString(), 60);
        if (!locker.trylock()) {
            logger.error("Get regain locker failed:{}", id);
            appendRegainQueue(id);
        }

        CnpcOrder order = cnpcOrderMapper.selectByPrimaryKey(id);
        if (order == null || order.getRegain().intValue() != 0 ||
                order.getState().intValue() != CnpcOrderState.Failed.getCode().intValue()) {
            logger.error("Pass regain task with:{}  order entity:{}", id, order);
        } else {
            CnpcOrder workedOrder = regainWork(order);
            if (workedOrder == null || workedOrder.getRegain().intValue() < 1) {
                logger.info("requeue in regain task {}", id);
                appendRegainQueue(id);
            }
        }

        locker.unlock();

        return null;
    }


    @Transactional
    public CnpcOrder regainWork(CnpcOrder order) {
        if (!CnpcOrderState.Failed.getCode().equals(order.getState()) || order.getRegain().intValue() != 0) {
            logger.error("RegainWork error , state: {},  regainmask:{}", order.getState(), order.getRegain());
            return order;
        }

        boolean c = false;
        if (BitMask.isSeted(order.getThroughMask().intValue(), THROUGH_PTGC_MASK_BIT)) {
            BillFlow flow = Rhizine.revocation(order.getRhiFlow(), order.getUflow().toString() + "R");
            loggingOperation(flow, order, CnpcOrderInvokeLogType.Regain);

            if (FlowHelper.isNormal(flow)) {
                c = true;
            } else {
                if (FlowHelper.isNetworkNormal(flow)) {
                    if (flow.getBillObject() != null) {
                        int status = flow.getBillObject().getStatus().intValue();
                        if (status == 1105) { //重复订单
                            c = true;
                        }

                        if (status == 1115) { //状态不正确
                            //TOOD 查询这笔订单状态
                        }
                    }
                }
            }
        }

        if (c) {
            int regain = CnpcRegainBit.Revocation;

            order.setRegain(regain);
            CnpcOrder ue = new CnpcOrder();
            ue.setId(order.getId());
            ue.setRegain(order.getRegain());
            ue.setRegainAt(new Date());
            ue.setState(CnpcOrderState.CreditLineRegained.getCode());

            if (cnpcOrderMapper.updateByPrimaryKeySelective(ue) < 1) {
                logger.error("Update order regain info error {}", order);
//                throw new BillException(OrderError.RegainFaild);
            }
        }

        return order;
    }


    /**
     * @param id
     * @return
     */
    private CnpcOrder throughWork(Long id) {
        CnpcOrder order = cnpcOrderMapper.selectByPrimaryKey(id);
        if (order == null) {
            logger.error("cnpc recharge order not exist: {}", id);
            return null;
        }

        if (!CnpcOrderState.Initialize.getCode().equals(order.getState())) {
            logger.error("cnpc recharge order:{} state is not [Initialize], current is:{}",
                    id, order.getState());
            return order;
        }

        if (!BitMask.isSeted(order.getThroughMask().intValue(), THROUGH_PTGC_MASK_BIT)) {
            rhizineThrough(order);
        }

        if (!BitMask.isSeted(order.getThroughMask().intValue(), THROUGH_PTGC_MASK_BIT)) {
            //pgtc through failed, not pass to provider.
            return order;
        }

        if (!BitMask.isSeted(order.getThroughMask().intValue(), THROUGH_PROVIDER_MASK_BIT)) {
            channelThrough(order);
        }

        return order;
    }

    /**
     * @param order
     */
    private void rhizineThrough(CnpcOrder order) {
        CnpcOrder ue = new CnpcOrder();
        ue.setId(order.getId());
        int throughMask = order.getThroughMask().intValue();

        int pgtcThroughCount = order.getRhiThroughs() + 1;
        order.setRhiThroughs(pgtcThroughCount);
        ue.setRhiThroughs(pgtcThroughCount);

        BillFlow flow = Rhizine.deduction(order.getUid(), order.getUflow(), order.getFee(),
                order.getPromo(), order.getPromoid(),
                Director.from(order.getDirector().byteValue()));

        if (FlowHelper.isNormal(flow)) {
            throughMask = BitMask.setBit(throughMask, THROUGH_PTGC_MASK_BIT, true);

            if (!(flow.getBillObject().getData() instanceof Map)) {

            }

            Map<String, Object> logic = (Map<String, Object>) flow.getBillObject().getData();
            //TODO 检查结果是否正确 获取交易ID

            ue.setThroughMask(throughMask);
            ue.setRhiFlow((Long) logic.get("id"));

            order.setThroughMask(throughMask);
            order.setRhiFlow(ue.getRhiFlow());
        } else {
            logger.error("{}", flow);


            if (pgtcThroughCount < MAX_PGTC_THROUGH_COUNT && FlowHelper.rechargeNeedRetry(flow)) {
                appendThroughQueue(order);
            } else {
                if (null != flow) {
                    String failureReason = flow.getBillObject().getMessage();
                    ue.setFailureReason(failureReason);
                    order.setFailureReason(failureReason);
                }

                ue.setState(CnpcOrderState.Failed.getCode());
                order.setState(CnpcOrderState.Failed.getCode());

                logger.info("Order:{} PGTC Through count is:{}, not through, failureReason:{}",
                        order.getId(), pgtcThroughCount, order.getFailureReason());
            }
        }

        if (cnpcOrderMapper.updateByPrimaryKeySelective(ue) < 1) {
            logger.error("Update perssited cnpc order:{} not success", ue.getId());
        }

        loggingOperation(flow, order, CnpcOrderInvokeLogType.PGTCThrought);
    }

    /**
     * @param order
     */
    private void channelThrough(CnpcOrder order) {
        CnpcOrder ue = new CnpcOrder();
        ue.setId(order.getId());
        int throughMask = order.getThroughMask().intValue();

        Director director = Director.from(order.getDirector());
        if (director == null) {
            throw new BillException(OrderError.UnkownDirector);
        }

        CNPC cnpc;
        if (order.getChannel() != null) {
            cnpc = new CNPC(order.getChannel());
        } else {
            cnpc = new CNPC(order.getFee(), director);
        }
        cnpc.setCnpcConfig(cnpcConfig);

        if (cnpc.getProvider() == null) {
            logger.error("Unable get provider for order:{} fee:{}, director:{}",
                    order.getId(), order.getFee(), order.getDirector());
            return;
        }

        int channelThroughCount = order.getChannelThroughs() + 1;
        ue.setChannelThroughs(channelThroughCount);
        order.setChannelThroughs(channelThroughCount);

        CnpcProviderResult providerResult = cnpc.charge(director, order.getId(),
                order.getDirectorCard(), order.getFee());
        if (providerResult.isSubmitSuccess()) {

            throughMask = BitMask.setBit(throughMask, THROUGH_PROVIDER_MASK_BIT, true);

            if (!StringUtils.isEmpty(providerResult.getProviderOrder())) {
                ue.setChannelOrder(providerResult.getProviderOrder());
                order.setChannelOrder(ue.getChannelOrder());
            }

            ue.setChannel(cnpc.getProvider().id());
            ue.setThroughMask(throughMask);
            ue.setState(CnpcOrderState.Requested.getCode());

            order.setChannel(cnpc.getProvider().id());
            order.setThroughMask(throughMask);
            order.setState(CnpcOrderState.Requested.getCode());
        } else {
            ue.setChannel(cnpc.getProvider().id());
            if (providerResult.isNeedRetry()) {
                if (channelThroughCount < MAX_PROVIDER_THROUGH_COUNT) {
                    appendThroughQueue(order);
                } else {
                    logger.info("Order:{} Provider Through count is:{}, not through", order.getId(), channelThroughCount);
                }
            } else {
                if (providerResult.getFailureReason() != null) {
                    ue.setFailureReason(providerResult.getFailureReason());
                    order.setFailureReason(providerResult.getFailureReason());
                }

                ue.setState(CnpcOrderState.Failed.getCode());
                order.setState(CnpcOrderState.Failed.getCode());
            }
        }

        if (cnpcOrderMapper.updateByPrimaryKeySelective(ue) < 1) {
            logger.error("Update perssited cnpc order:{} not success", ue.getId());
        }

        loggingOperationByCNPCProviderLog(providerResult.getProviderLog(),
                order, CnpcOrderInvokeLogType.ProviderThrough);
    }


    /**
     * @param flow
     * @param cnpcOrder
     */
    private void loggingOperation(BillFlow flow, CnpcOrder cnpcOrder, CnpcOrderInvokeLogType logType) {
        String ans = flow.getAns();
        if (!StringUtils.isEmpty(flow.getErr())) {
            if (ans == null) {
                ans = "";
            } else {
                ans += "\n\n";
            }

            ans += flow.getErr();
        }

        logging(flow.getAsk(), ans, flow.getHttpcode(),
                flow.getAskat(), flow.getAnsat(), cnpcOrder, logType);
    }

    /**
     * @param providerLog
     * @param cnpcOrder
     * @param logType
     */
    private void loggingOperationByCNPCProviderLog(CnpcProviderLog providerLog, CnpcOrder cnpcOrder,
                                                   CnpcOrderInvokeLogType logType) {
        logging(providerLog.getRequest(), providerLog.getResponse(), providerLog.getHttpCode(),
                providerLog.getRequestAt(), providerLog.getResponseAt(), cnpcOrder, logType);
    }

    private void logging(String req, String respone, Integer httpCode, Date reqAt, Date responseAt,
                         CnpcOrder cnpcOrder, CnpcOrderInvokeLogType logType) {
        CnpcOrderInvokeLog log = new CnpcOrderInvokeLog();
        log.setCreatedAt(new Date());
        log.setOrderId(cnpcOrder.getId());
        log.setRequest(req);

        if (respone == null) {
            respone = "";
        }

        if (httpCode == null) {
            httpCode = -1;
        }

        log.setResponse(respone);
        log.setHttpcode(httpCode);

        log.setRequestAt(reqAt);

        if (responseAt == null) {
            responseAt = new Date();
        }
        log.setResponseAt(responseAt);
        log.setType(logType.ordinal());

        cnpcOrderInvokeLogMapper.insert(log);
    }

    @Override
    public void debugRequeue(Long id, Integer type) {
        int itype = type.intValue();
        MQPublisher.DelayInterval delayInterval = MQPublisher.DelayInterval.IMMEDIATELY;
        if (itype == 98765) {
            appendThroughQueue(id, delayInterval);
        }

        if (itype == 98766) {
            appendRegainQueue(id);
        }
    }

    /**
     * @param id
     * @param delayInterval
     * @return
     */
    private boolean appendThroughQueue(Long id, MQPublisher.DelayInterval delayInterval) {
        return publishToCnpcExchange(MQDefiner.RK_THROUGH, id, delayInterval);
    }

    /**
     * @param order
     * @return
     */
    @Override
    public boolean appendThroughQueue(CnpcOrder order) {
        MQPublisher.DelayInterval delayInterval = CnpcOrderHelper.throughDelayInterval(order);
        return publishToCnpcExchange(MQDefiner.RK_THROUGH, order.getId(), delayInterval);
    }

    /**
     * @param order
     * @return
     */
    @Override
    public boolean appendInquireQueue(CnpcOrder order) {
        MQPublisher.DelayInterval delayInterval = MQPublisher.DelayInterval.M2;
        if (order.getInquires() >= 5) {
            delayInterval = MQPublisher.DelayInterval.M5;
        }

        return publishToCnpcExchange(MQDefiner.RK_INQUIRE, order.getId(), delayInterval);
    }

    /**
     * @param order
     * @return
     */
    @Override
    public boolean appendRegainQueue(Long id) {
        MQPublisher.DelayInterval delayInterval = MQPublisher.DelayInterval.M5;
        return publishToCnpcExchange(MQDefiner.RK_REGAIN, id, delayInterval);
    }


    /**
     * @param routeKey
     * @param value
     * @param delayInterval
     * @return
     */
    private boolean publishToCnpcExchange(String routeKey, Object value, MQPublisher.DelayInterval delayInterval) {
        boolean result = false;
        try {
            if (connection == null || !connection.isOpen()) {
                connection = connectionFactory.newConnection();
            }
            MQPublisher.cnpc(connection, routeKey, value, delayInterval);
            result = true;
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return result;
    }


}

