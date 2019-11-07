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

package com.petroun.devourerhizine.provider.cnpc;

import cn.gotoil.bill.model.BasicEnum;

public enum Director implements BasicEnum {

    CNPC(1,"CNPC"),
    SINOPEC(2, "Sinopec");

    private Integer code;
    private String name;

    Director(Integer code, String name) {
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

    public static Director from(Byte b){
       if (b.byteValue()==CNPC.getCode().byteValue()) {
           return CNPC;
       }

       if (b.byteValue() == SINOPEC.getCode().byteValue()) {
           return SINOPEC;
       }

        return null;
    }

}
