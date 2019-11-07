package com.petroun.devourerhizine.enums;

import cn.gotoil.bill.exception.BillError;

public enum MerchantError implements BillError {

    LackOfCredit(1100, "Lack of credit"),
    UnsupportedBusiness(1101, "This business is not supported for your account"),
    UnsupportedPersonalModel(1102, "Your accoutn can not use personal model.");

    private int code;
    private String message;

    MerchantError(int code, String message) {
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