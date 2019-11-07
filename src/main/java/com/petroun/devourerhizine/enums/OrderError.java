package com.petroun.devourerhizine.enums;

import cn.gotoil.bill.exception.BillError;

public enum OrderError implements BillError {

    UnkownError(1200, "Unkown error when create order"),
    NotExist(1204, "Order not found."),
    RegainFaild(1210, "Regain failed"),
    UnsupportedAmount(1205, "unsupported amount"),
    DuplicateOrder(1206, "Duplicate order id"),
    RequirePayPassword(1207, "Card pay password is required"),
    UnkownDirector(1208,"Unkonw director");

    private int code;
    private String message;

    OrderError(int code, String message) {
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