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

public enum FuelOrderState implements BasicEnum {
    Initialize(1, "Initialize"),
    Failed(99, "Failed"),
    Successed(0, "Successed");

    private Integer code;
    private String name;

    FuelOrderState(Integer code, String name) {
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