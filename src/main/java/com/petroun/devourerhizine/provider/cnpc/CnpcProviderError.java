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

import cn.gotoil.bill.exception.BillError;

public enum  CnpcProviderError implements BillError {

    UnableFindSupportedAmountProvider(2300, "UnableFindSupportedAmountProvider"),
    UnableSupportedThisAmount(2301, "UnableSupportedThisAmount");

    private int code;
    private String message;

    CnpcProviderError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
