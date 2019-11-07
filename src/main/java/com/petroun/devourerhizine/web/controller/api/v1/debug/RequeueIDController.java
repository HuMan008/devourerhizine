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

package com.petroun.devourerhizine.web.controller.api.v1.debug;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import com.petroun.devourerhizine.service.cnpc.CnpcRechargeService;
import com.petroun.devourerhizine.web.controller.api.v1.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Authentication(authenticationType = AuthenticationType.None)
public class RequeueIDController extends Controller {

    @Autowired
    private CnpcRechargeService cnpcRechargeService;

    @RequestMapping(value = "/debug/requeue/{orderId}/{queueType}")
    public Object requeueAction(@PathVariable Long orderId, @PathVariable Integer queueType) {
        cnpcRechargeService.debugRequeue(orderId, queueType);
        return null;
    }
}
