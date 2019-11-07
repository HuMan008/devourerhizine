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

package com.petroun.devourerhizine.web.controller.api.v1.sinopec;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.bill.web.message.BillApiResponse;
import com.petroun.devourerhizine.enums.OrderError;
import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.service.cnpc.CnpcOrderService;
import com.petroun.devourerhizine.service.cnpc.CnpcRechargeService;
import com.petroun.devourerhizine.web.controller.api.v1.Controller;
import com.petroun.devourerhizine.web.message.request.sinopec.Recharge;
import com.petroun.devourerhizine.web.message.response.sinopec.OrderRHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Authentication(authenticationType = AuthenticationType.Signature)
public class OrderController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private CnpcOrderService cnpcOrderService;

    @Autowired
    private CnpcRechargeService rechargeService;


    @RequestMapping(value = "/sinopec/order/{orderId:^\\d{19}$}", method = RequestMethod.GET)
    public Object getAction(@PathVariable Long orderId) {
        CnpcOrder cnpcOrder = cnpcOrderService.cnpcOrder(orderId);
        if (cnpcOrder == null) {
            return new BillApiResponse(OrderError.NotExist);
        }

        return OrderRHelper.FromOrderEntity(cnpcOrder);
    }


    @RequestMapping(value = "/sinopec/order", method = RequestMethod.POST)
    public Object postAction(@Valid @RequestBody Recharge recharge, BindingResult bindingResult) {
        int amount = recharge.getAmount().intValue();
        if (amount != 50000 && amount != 100000
                && amount != 10000 && amount != 20000) {
            return new BillApiResponse(OrderError.UnsupportedAmount);
        }

        CnpcOrder order = rechargeService.recharge(recharge, Controller.API_VERSION);
        return OrderRHelper.FromOrderEntity(order);
    }
}
