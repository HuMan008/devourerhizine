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


import com.petroun.devourerhizine.provider.cnpc.*;
import com.google.common.base.Optional;
import com.petroun.devourerhizine.provider.cnpc.xunyin.entity.XunyinCreate;
import com.petroun.devourerhizine.provider.cnpc.xunyin.entity.XunyinInquire;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XunyinHelper {

    private static Map<String, Boolean> CreateStateMap = new HashMap<>();
    private static Set<String> inquireFailedSets = new HashSet<>();

    static {
        CreateStateMap.put("0000", true);
        CreateStateMap.put("8888", true);
        CreateStateMap.put("8008", true); //系统维护中
        CreateStateMap.put("8010", true); //系统未创建订单


        inquireFailedSets.add("8019"); //
        inquireFailedSets.add("8020");
        inquireFailedSets.add("8030");
        inquireFailedSets.add("9099");
        inquireFailedSets.add("8012");
    }

    public static void createResultProcess(CnpcProviderResult result, XunyinCreate create) {
        boolean sret = false;
        if (!StringUtils.isEmpty(create.getOrder_no())) {
            sret = CreateStateMap.getOrDefault(create.getCode(), true);
        }

        result.setSubmitSuccess(sret);
        if (result.isSubmitSuccess()) {
            result.setProviderOrder(create.getOrder_no());
        } else {
            result.setFailureReason("系统错误:PC02_06:" + create.getMsg());
        }
    }

    public static void inquireResultProcess(CnpcProviderInquireResult result, XunyinInquire inquire) {
        result.setFailureReason("");
        result.setResult(Optional.absent());
        result.setExist(true);
        result.setProviderOrder(inquire.getOrderid());
        result.setNeedRetry(false);

        if ("0000".equals(inquire.getState())) {
            result.setNeedRetry(true);
            return;
        }

        if ("8888".equals(inquire.getState()) ) {
            result.setResult(Optional.of(true));
        } else if (inquireFailedSets.contains(inquire.getState())) {
            result.setResult(Optional.of(false));
        } else if("8021".equals(inquire.getState())) {
            result.setNeedRetry(true);
            result.setResult(Optional.absent());
        }
    }
}
