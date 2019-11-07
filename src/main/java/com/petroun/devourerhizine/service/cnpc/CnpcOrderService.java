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

public interface CnpcOrderService {

    CnpcOrder persist(Recharge recharge, Byte apiVersion);

    CnpcOrder cnpcOrder(Long orderId);
}
