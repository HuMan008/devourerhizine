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

package com.petroun.devourerhizine.enums;

import cn.gotoil.bill.exception.BillError;

public enum FinanceError implements BillError {
    DayError(1300, "Date string validated error"),
    DateError(1301, "Can not query this day at now");

    private int code;
    private String message;

    FinanceError(int code, String message) {
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