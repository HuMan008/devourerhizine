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

package com.petroun.devourerhizine.classes.cnpc;


import com.petroun.devourerhizine.classes.rabbitmq.MQPublisher;
import com.petroun.devourerhizine.model.entity.CnpcOrder;

public class CnpcOrderHelper {

    public static MQPublisher.DelayInterval generalThroughDelayInterval(short throughCount) {

        if (throughCount == 0) {
            return MQPublisher.DelayInterval.IMMEDIATELY;
        }

        if (throughCount == 1) {
            return MQPublisher.DelayInterval.M1;
        }

        if (throughCount < 5) {
            return MQPublisher.DelayInterval.M2;
        }


        return MQPublisher.DelayInterval.M5;
    }

    public static MQPublisher.DelayInterval throughDelayInterval(CnpcOrder order) {
        if ((order.getThroughMask().intValue() & 0x01) == 0x00) {
            return generalThroughDelayInterval(order.getRhiThroughs().shortValue());
        }

        if ((order.getThroughMask().intValue() & 0x02) == 0x00) {
            return generalThroughDelayInterval(order.getChannelThroughs().shortValue());
        }


        return MQPublisher.DelayInterval.M2;
    }
}
