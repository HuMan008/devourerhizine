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

package com.petroun.devourerhizine.provider.cnpc.xunyin;



import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XunyinConfiger {

    private static final String cpid = "1549";
    private static final String balanceUrl = "http://gateway.xunyin.com/api/QueryMerchant";
    private static final String inquireUrl = "http://gateway.xunyin.com/api/QueryOrder";
    private static final String throughUrl = "http://gateway.xunyin.com/api/Pay";
    private static final String templateUrl = "http://gateway.xunyin.com/api/Template.aspx";

    @Autowired
    private OptionService optionService;

    public static String getCpid() {
        return cpid;
    }

    public static String getBalanceUrl() {
        return balanceUrl;
    }

    public static String getInquireUrl() {
        return inquireUrl;
    }

    public static String getThroughUrl() {
        return throughUrl;
    }


    public String key() {
        return optionService.get(OptionKeys.XunyinKey);
    }


}
