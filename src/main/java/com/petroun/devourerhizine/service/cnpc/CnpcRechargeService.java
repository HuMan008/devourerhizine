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

package com.petroun.devourerhizine.service.cnpc;

import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.web.message.request.sinopec.Recharge;

public interface CnpcRechargeService {

    CnpcOrder recharge(Recharge recharge, Byte apiVersion);

    CnpcOrder through(Long id);

    CnpcOrder inquire(Long id, boolean disableAppendInquireQueue);

    CnpcOrder inquire(CnpcOrder cnpcOrder, boolean disableAppendInquireQueue);

    CnpcOrder regain(Long id);

    void debugRequeue(Long id, Integer type);


    boolean appendThroughQueue(CnpcOrder order);
    boolean appendInquireQueue(CnpcOrder order);
    boolean appendRegainQueue(Long id);
}
