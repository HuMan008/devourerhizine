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

import cn.gotoil.bill.classes.billid.BillId;
import cn.gotoil.bill.exception.BillException;
import com.petroun.devourerhizine.enums.OrderError;
import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.model.enums.CnpcOrderState;
import com.petroun.devourerhizine.model.mapper.CnpcOrderMapper;
import com.petroun.devourerhizine.provider.cnpc.CnpcHelper;
import com.petroun.devourerhizine.service.cnpc.CnpcOrderService;
import com.petroun.devourerhizine.service.cnpc.CnpcRechargeService;
import com.petroun.devourerhizine.web.message.request.sinopec.Recharge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CnpcOrderServiceImpl implements CnpcOrderService {

    @Autowired
    private CnpcOrderMapper cnpcOrderMapper;


    @Autowired
    private CnpcRechargeService cnpcRechargeService;

    private static Logger logger = LoggerFactory.getLogger(CnpcOrderServiceImpl.class);

    @Override
    @Transactional
    public CnpcOrder persist(Recharge recharge, Byte apiVersion) {

        CnpcOrder order = new CnpcOrder();
        order.setDirector(CnpcHelper.directorFromDirectorCard(recharge.getCard()));
        order.setFee(recharge.getAmount());
        order.setMobile(recharge.getMobile());
        order.setId(new BillId().longValue());
        order.setDirectorCard(recharge.getCard());

        order.setUid(recharge.getUid());
        order.setUflow(recharge.getOrderid());
        order.setUextra(recharge.getExtra());
        order.setPromo(recharge.getPromo());
        order.setPromoid(recharge.getPromoid());

        order.setState(CnpcOrderState.Initialize.getCode());
        order.setInquires(0);
        order.setThroughMask(0);
        order.setRegain(0);
        order.setChannelThroughs(0);
        order.setRhiThroughs(0);

        order.setCreatedAt(new Date());
        order.setUpdatedAt(order.getCreatedAt());


        try {
            cnpcOrderMapper.insert(order);
        } catch (DuplicateKeyException ex) {
            throw new BillException(OrderError.DuplicateOrder);
        }

        return order;
    }

    @Override
    public CnpcOrder cnpcOrder(Long orderId) {
        CnpcOrder cnpcOrder = cnpcOrderMapper.selectByPrimaryKey(orderId);

        if (null == cnpcOrder) {
            throw new BillException(OrderError.NotExist);
        }

        int intStateValue = cnpcOrder.getState().intValue();
        if (intStateValue < CnpcOrderState.Successed.getCode().intValue()) {
            if (intStateValue == CnpcOrderState.Requested.getCode().intValue()) {
                cnpcOrder = cnpcRechargeService.inquire(cnpcOrder, true);
            }
        }

        return cnpcOrder;
    }
}


