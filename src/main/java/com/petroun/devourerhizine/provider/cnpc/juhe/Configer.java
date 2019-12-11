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

package com.petroun.devourerhizine.provider.cnpc.juhe;

public class Configer {

    private static final String throughUrl = "http://op.juhe.cn/ofpay/sinopec/onlineorder";

    private static final String inquireUrl = "http://op.juhe.cn/ofpay/sinopec/ordersta";

    private static final String balanceUrl = "http://op.juhe.cn/ofpay/mobile/yue";

    private static final String key = "44bba72116d3badb45c5b0f7ee0a9724";

    private static final String balanceKey = "045a8a4d1e29acb826d18e27965483d1";

    public static String getThroughUrl() {
        return throughUrl;
    }

    public static String getInquireUrl() {
        return inquireUrl;
    }

    public static String getBalanceUrl() {
        return balanceUrl;
    }

    public static String getKey() {
        return key;
    }

    public static String getBalanceKey() {
        return balanceKey;
    }
}
