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

package com.petroun.devourerhizine.web.message.response.sinopec;


import com.petroun.devourerhizine.classes.BitMask;
import com.petroun.devourerhizine.model.entity.CnpcOrder;

public class OrderRHelper {
    // 贵彬啊，我这边失败了。别查了。
    private static int FailCode1001 =1001;
    //需要贵彬再来查
    private static int FailCode1009 =1009;
    /**
     * @param entity
     * @return
     */
    public static Order FromOrderEntity(CnpcOrder entity) {
        Order order = new Order();
        order.setId(entity.getId());
        order.setUid(entity.getUid());
        order.setDirector(entity.getDirector());
        order.setAmount(entity.getFee());
        order.setDate(entity.getCreatedAt());
        order.setRhiflow(entity.getRhiFlow());
        order.setCard(entity.getDirectorCard());

//        if (entity.getState().intValue() == CnpcOrderState.CreditLineRegained.getCode().intValue()) {
//            order.setState(CnpcOrderState.Failed.getCode());
//        } else {
//            order.setState(entity.getState());
//        }
        order.setState(entity.getState());
        if( BitMask.isSeted(entity.getThroughMask().intValue(), 0) && entity.getState() == FailCode1001){
            order.setState(FailCode1009);
        }

        order.setFailureReason(entity.getFailureReason());
        order.setExtra(entity.getUextra());
        order.setOrderid(entity.getUflow());
        order.setChan(entity.getChan());
        return order;
    }
}
