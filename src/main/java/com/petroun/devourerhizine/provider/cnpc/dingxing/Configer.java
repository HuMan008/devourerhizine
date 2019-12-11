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

package com.petroun.devourerhizine.provider.cnpc.dingxing;

public class Configer {
//    secret: Ikw83Qa2sC0qrLAbTws9V8u9qDbXnOTz
//    private static final String apiurl = "http://testapi.jiayouka.cn:11140/";
//    private static final String account = "18000000003";


    private static final String apiurl = "http://api.jiayouka.cn:11140/";
    private static final String account = "17784770437";  ///  15922588131


    private static final String rechargeUrl = apiurl + "trade/gateway";
    private static final String inquireUrl = apiurl + "trade/queryOrder";
    private static final String balanceUrl = apiurl + "trade/queryBalance";

    public static String getApiurl() {
        return apiurl;
    }

    public static String getAccount() {
        return account;
    }

    public static String getRechargeUrl() {
        return rechargeUrl;
    }

    public static String getInquireUrl() {
        return inquireUrl;
    }

    public static String getBalanceUrl() {
        return balanceUrl;
    }

}
