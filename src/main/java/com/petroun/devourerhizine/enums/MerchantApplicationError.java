package com.petroun.devourerhizine.enums;

import cn.gotoil.bill.exception.BillError;

public enum MerchantApplicationError implements BillError {

    StateError(1000, "merchant application state is error");

    private int code;
    private String message;

    MerchantApplicationError(int code, String message) {
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
