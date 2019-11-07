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

package com.petroun.devourerhizine.provider.cnpc.qianxing;

import com.petroun.devourerhizine.provider.cnpc.*;
import com.google.common.base.Optional;
import com.petroun.devourerhizine.provider.cnpc.qianxing.entity.CreateResponse;
import com.petroun.devourerhizine.provider.cnpc.qianxing.entity.InquireResponse;

/**
 *
 */
@SuppressWarnings("all")
public class QXHelper {

    private static final String UNDERWAY = "UNDERWAY";
    private static final String FAILED = "FAILED";
    private static final String SUCCESS = "SUCCESS";
    private static final String REQUEST_FAILED = "REQUEST_FAILED";

    /**
     * @param createResponse
     * @return
     */
    public static boolean createSuccess(CreateResponse createResponse) {
        //252该订单已存在
        if (createResponse.getFailedCode().equals(252)) {
            return true;
        }

        if (!createResponse.getFailedCode().equals(0)) {
            return false;
        }

        return UNDERWAY.equals(createResponse.getOrderStatus());
    }


    /**
     * @param result
     * @param inquireResponse
     */
    public static void processInquireResult(CnpcProviderInquireResult result, InquireResponse inquireResponse) {
        result.setFailureReason(inquireResponse.getFailedReason());
        result.setResult(Optional.absent());

//        if (!inquireResponse.getFailedCode().equals(0)) {
//            result.setResult(Optional.of(false));
//            return;
//        }

        result.setExist(true);
        result.setProviderOrder(inquireResponse.getOrderNo());


        if (REQUEST_FAILED.equals(inquireResponse.getOrderStatus())) {
            result.setNeedRetry(true);
            
            if (Integer.valueOf(256).equals(inquireResponse.getFailedCode())) {  //渠道产品未开通或维护中
                result.setResult(Optional.of(false));
            }
            return;
        }

        if (UNDERWAY.equals(inquireResponse.getOrderStatus())) {
            result.setNeedRetry(true);
            return;
        }

        if (FAILED.equals(inquireResponse.getOrderStatus())) {
            result.setResult(Optional.of(false));
            return;
        }

        if (SUCCESS.equals(inquireResponse.getOrderStatus())) {
            result.setResult(Optional.of(true));
        }


    }

}
