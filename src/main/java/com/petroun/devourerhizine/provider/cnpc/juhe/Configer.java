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

    private static final String key = "7ec82176fdf041639bde62201a50b262";

    private static final String balanceKey = "82afd61ca8be09137e065eec8f536c44";

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
