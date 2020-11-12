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

package com.petroun.devourerhizine;

import cn.gotoil.bill.tools.ObjectHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.petroun.devourerhizine.classes.rabbitmq.MQDefiner;
import com.petroun.devourerhizine.classes.rabbitmq.SuppressRabbitConsumer;
import com.petroun.devourerhizine.classes.tools.HttpUtils;
import com.petroun.devourerhizine.enums.EnumOilSendStatus;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.model.View.gt.ViewOilTrans;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.provider.petroun.Rhizine;
import com.petroun.devourerhizine.service.OptionService;
import com.petroun.devourerhizine.service.cnpc.CnpcRechargeService;
import com.petroun.devourerhizine.service.oil.CardService;
import com.petroun.devourerhizine.service.oil.GotoilService;
import com.petroun.devourerhizine.service.oil.OilService;
import com.rabbitmq.client.*;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(RabbitMQRunner.class);

    @Autowired
    private Connection connection;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private CnpcRechargeService cnpcRechargeService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private GotoilService gotoilService;

    @Autowired
    private CardService cardService;

    @Autowired
    private OilService oilService;

    @Override
    public void run(String... args) throws Exception {
        optionService.reload();

        Rhizine.configure(optionService.get(OptionKeys.RHIZINE_HOST),
                optionService.get(OptionKeys.RHIZINE_XU),
                optionService.get(OptionKeys.RHIZINE_SECRET));

        logger.info("============start MQ runner============");
        MQDefiner.defineDelayExchangeAndQueues(connection);
        cnpcThrough();
        cnpcInquire();
        cnpcRegain();

        gotoilRefuel();
        gotoilTransQuery();
        ;
    }

    /**
     * @throws Exception
     */
    private void cnpcThrough() throws Exception {

//        for (int i = 0; i < 2; i++) {
        logger.info("============start cnpcThrough worker============");
        Channel channel = MQDefiner.ThroughChannel(applyConnection(), 32);
        CnpcThroughConsumer througher = new CnpcThroughConsumer(channel);
        channel.basicConsume(MQDefiner.Q_THROUGH, false, througher);
//        }
    }

    /**
     * @throws Exception
     */
    private void cnpcInquire() throws Exception {
        logger.info("============start cnpcInquire worker============");
//        for (int i = 0; i < 5; i++) {
        Channel channel = MQDefiner.InquireChannel(applyConnection(), 32);
        CnpcInquireConsumer inquirer = new CnpcInquireConsumer(channel);
        channel.basicConsume(MQDefiner.Q_INQUIRE, false, inquirer);
//        }
    }


    /**
     * @throws Exception
     */
    private void cnpcRegain() throws Exception {
        logger.info("============start cnpcRegain worker============");
        Channel channel = MQDefiner.RegainChannel(applyConnection(), 8);
        CnpcRegainConsumer regainer = new CnpcRegainConsumer(channel);
        channel.basicConsume(MQDefiner.Q_REGAIN, false, regainer);
    }


    private void gotoilRefuel() throws Exception {
        logger.info("============start gotoil_qr_refuel  worker============");
        Channel channel = MQDefiner.gotoilRefuelChannel(applyConnection(), 8);
        GotoilRefuleConsumer gotoilQr = new GotoilRefuleConsumer(channel);
        channel.basicConsume(MQDefiner.Q_REFUEL, false, gotoilQr);
    }


    private void gotoilTransQuery() throws Exception {
        logger.info("============start gotoil_trans_query  worker============");
        Channel channel = MQDefiner.gotoilTransQueryChannel(applyConnection(), 8);
        GotoilTransQueryConsumer transQueryConsumer = new GotoilTransQueryConsumer(channel);
        channel.basicConsume(MQDefiner.Q_TRANSQUERY, false, transQueryConsumer);
    }


    /**
     * @return
     */
    private Connection applyConnection() {
        if (connection == null || !connection.isOpen()) {
            try {
                connection = connectionFactory.newConnection();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        return connection;
    }


    /**
     *
     */
    private class CnpcThroughConsumer extends SuppressRabbitConsumer {

        public CnpcThroughConsumer(Channel channel) {
            super(channel);
        }

        public void startWork() {
            try {
                cnpcThrough();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
                Long id = ObjectHelper.getObjectMapper().readValue(body, Long.class);
                logger.info("Handle CNPC Through Task:{}", id);
                cnpcRechargeService.through(id);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }

    /**
     *
     */
    private class CnpcInquireConsumer extends SuppressRabbitConsumer {
        public CnpcInquireConsumer(Channel channel) {
            super(channel);
        }

        public void startWork() {
            try {
                cnpcInquire();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
//                CnpcOrder cnpcOrder = ObjectHelper.getObjectMapper().readValue(body, CnpcOrder.class);
//                logger.info("Handle CNPC Inquire Task:{}", cnpcOrder);

                Long id = ObjectHelper.getObjectMapper().readValue(body, Long.class);
                cnpcRechargeService.inquire(id, false);
                getChannel().basicAck(envelope.getDeliveryTag(), false);

            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }


    /**
     *
     */
    private class CnpcRegainConsumer extends SuppressRabbitConsumer {
        public CnpcRegainConsumer(Channel channel) {
            super(channel);
        }

        public void startWork() {
            try {
                cnpcRegain();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
                Long id = ObjectHelper.getObjectMapper().readValue(body, Long.class);
                logger.info("Handle CNPC Regain Task:{}", id);
                cnpcRechargeService.regain(id);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }


    /**
     * 二维码加油通知
     */
    private class GotoilRefuleConsumer extends SuppressRabbitConsumer {
        public GotoilRefuleConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void startWork() {
            try {
                gotoilRefuel();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                   byte[] body) throws IOException {
            try {
                //todo 交易成功后国通加油通知
                /**
                 * 交易成功后，通知可伶
                 */
                logger.info("国通加油通知{}", new String(body));
                String useId = ObjectHelper.getObjectMapper().readValue(body, String.class);
                OilCardUse use = cardService.queryById(useId);
                if (use == null) {
                    logger.info("收到一条ID不存在的通知 ，确认消费后 ，返回{}", useId);
                    getChannel().basicAck(envelope.getDeliveryTag(), false);
                    return;
                }
                if(use.getSendCount() > 5){
                    OilCardUse updateUse = new OilCardUse();
                    updateUse.setId(use.getId());
                    updateUse.setStatus(EnumOilSendStatus.fail.getCode());
                    cardService.updateUse(use);
                }
                if(use.getStatus() == EnumTranStatus.success.getCode()){
                    ViewOilTrans oilTransView = new ViewOilTrans();
                    oilTransView.setStationId(use.getStation());
                    oilTransView.setStationName(use.getStationName());
                    oilTransView.setId(use.getId());
                    oilTransView.setFee(String.valueOf(use.getAmount()));
                    oilTransView.setMobile(use.getMobile());
                    ObjectMapper mapper = new ObjectMapper();
                    Response response = HttpUtils.okHttpPostByString(use.getSendUrl(),mapper.writeValueAsString(oilTransView));
                    boolean ok = false;
                    if (response != null && response.isSuccessful()) {
                        String result  = response.message();
                        if("ok".equalsIgnoreCase(result)){
                            OilCardUse updateUse = new OilCardUse();
                            updateUse.setId(use.getId());
                            updateUse.setSendStatus(EnumOilSendStatus.success.getCode());
                            cardService.updateUse(updateUse);
                            ok = true;
                        }
                    }
                    logger.info("国通加油通知",response.toString());
                    if(!ok){
                        int count = use.getSendCount() + 1;
                        gotoilService.appendGotoilTransSucessQueue(use.getId(), count);

                        OilCardUse updateUse = new OilCardUse();
                        updateUse.setId(use.getId());
                        updateUse.setSendCount(count);
                        cardService.updateUse(updateUse);

                    }
                }
                //use.getSendUrl();
                //count
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }


    /**
     * 交易查询通知
     */
    private class GotoilTransQueryConsumer extends SuppressRabbitConsumer {
        public GotoilTransQueryConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void startWork() {
            try {
                gotoilTransQuery();
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                   byte[] body) throws IOException {
            try {
                //todo 国通交易查询通知
                logger.info("国通交易查询通知{}", new String(body));
                String useId = ObjectHelper.getObjectMapper().readValue(body, String.class);
                OilCardUse queryCardUse = oilService.queryMobileCardTrans(useId);
                /**
                 * 获取二维码后，查询1易结果
                 * 成功后加入通知成功队列
                 */
                if(queryCardUse != null) {
                    if (queryCardUse.getStatus() == EnumTranStatus.Trading.getCode()) {
                        gotoilService.appendGotoilQueryQueue(useId);
                    } else {
                        gotoilService.appendGotoilTransSucessQueue(useId,0);
                    }
                }
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (InvalidFormatException ex) {
                logger.info("{}", ex);
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }
    }
}
