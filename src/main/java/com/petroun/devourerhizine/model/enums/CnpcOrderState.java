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

package com.petroun.devourerhizine.model.enums;

import cn.gotoil.bill.model.BasicEnum;

/**
 * cnpc recharge order state
 */
public enum CnpcOrderState implements BasicEnum {
    Initialize(1, "Initialize"),
    Requested(10, "Requested"),
    Successed(1000, "Successed"),
    Failed(1001, "Failed"),
    CreditLineRegained(1010, "CreditLineRegained");

    private Integer code;
    private String name;

    CnpcOrderState(Integer code, String name) {
        this.code = code;
        this.name = name;
    }



    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getComment() {
        return null;
    }
}
