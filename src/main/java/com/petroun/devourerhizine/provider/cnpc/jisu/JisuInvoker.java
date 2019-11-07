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

package com.petroun.devourerhizine.provider.cnpc.jisu;


import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.provider.cnpc.*;

import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.petroun.devourerhizine.service.OptionService;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JisuInvoker {


    @Autowired
    private OptionService optionService;

    private static Logger logger = LoggerFactory.getLogger(JisuInvoker.class);

    private static final String CREATE_URL = "http://api.jisuapi.com/fuelcardrecharge/recharge";
    private static final String INQUIRE_URL = "http://api.jisuapi.com/fuelcardrecharge/orderdetail";

    private String appKey() {
        return optionService.get(OptionKeys.JisuAppKey, "");
    }

    private String appSecret() {
        return optionService.get(OptionKeys.JisuAppSecret, "");
    }

    public CnpcProviderResult create(Long orderId, String card, Integer amount) {

        Map<String, Object> params = new HashMap<>();
        params.put("fuelcard", card);
        params.put("appkey", appKey());
        params.put("amount", amount);
        params.put("outorderno", orderId);
        String sign = amount.toString() + card + orderId.toString() + appSecret();
        sign = DigestUtils.md5Hex(sign);
        params.put("sign", sign);
        CnpcProviderResult result = new CnpcProviderResult();
        result.setOrderId(orderId);
        CnpcProviderLog providerLog = result.getProviderLog();
        providerLog.setRequestAt(new Date());

        try {
//            HttpRequest request = Unirest.get(CREATE_URL).headers(CnpcConfiger.getHeaders()).queryString(params);
//            providerLog.setRequest(ObjectHelper.jsonString(params));
//            HttpResponse<String> res = request.asString();
//            providerLog.setHttpCode(res.getStatus());
//            providerLog.setResponseAt(new Date());
//            String body = res.getBody();
//            providerLog.setResponse(body);

            HttpResponse<String> res = CnpcHelper.httpGetInvoke(CREATE_URL, params, providerLog);
            String body = providerLog.getResponse();

            if (CnpcHelper.isNormalHttpcode(res.getStatus())) {
                try {
                    JsonNode jsonNode = new JsonNode(body);
                    JSONObject root = jsonNode.getObject();
                    int status = root.getInt("status");
                    result.setSubmitSuccess(status == 0 || 212 == status);
                    if (status == 0) {
                        JSONObject resultLevel = root.getJSONObject("result");
                        result.setProviderOrder(resultLevel.getString("orderno"));
                    } else if (212 == status) {  //{"status":"212","msg":"商家订单号已存在","result":""}
                        result.setSubmitSuccess(true);
                    } else {
                        result.setFailureReason(root.getString("msg"));
                        result.setNeedRetry(false);
                    }
                } catch (Exception ex) {
                    logger.error("{}", ex);
                    logger.error("{}", body);
                }
            } else {
                logger.error("{}", body);
            }
        } catch (Exception ex) {
            providerLog.setHttpCode(0);
            providerLog.setResponse(ex.toString());
            result.setNeedRetry(true);
            result.setSubmitSuccess(true);
        }

        return result;
    }

    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderno", providerOrder);
        params.put("outorderno", orderId);
        params.put("appkey", appKey());
        CnpcProviderInquireResult result = new CnpcProviderInquireResult();
        result.setOrderId(orderId);
        CnpcProviderLog providerLog = result.getProviderLog();
        providerLog.setRequestAt(new Date());

        try {
//            HttpRequest request = Unirest.get(INQUIRE_URL).headers(CnpcConfiger.getHeaders()).queryString(params);
//            providerLog.setRequest(ObjectHelper.jsonString(params));
//            HttpResponse<String> res = request.asString();
//            providerLog.setHttpCode(res.getStatus());
//            providerLog.setResponseAt(new Date());
//            String body = res.getBody();
//            providerLog.setResponse(body);

            HttpResponse<String> res = CnpcHelper.httpPostInvoke(INQUIRE_URL, params, providerLog);
            String body = providerLog.getResponse();

            if (CnpcHelper.isNormalHttpcode(res.getStatus())) {
                try {
                    JsonNode jsonNode = new JsonNode(body);
                    JSONObject root = jsonNode.getObject();
                    if (root.has("result") ) {
                        JSONObject resultLevel = root.getJSONObject("result");
                        int status = root.getInt("status");
                        result.setExist(status == 0);
                        if (status == 0) {
                            int rstatus = resultLevel.getInt("rechargestatus");
                            result.setResult(statusFinally(rstatus));
                            result.setNeedRetry(true);
                        } else {
                            logger.error("{}", body);
                        }
                    } else {
                        logger.error("{}", body);
                    }
                } catch (Exception ex) {
                    logger.error("{}", ex);
                    result.setNeedRetry(true);
                }
            } else {
                logger.error("{}", body);
                result.setNeedRetry(true);
            }
        } catch (Exception ex) {
            providerLog.setHttpCode(0);
            providerLog.setResponse(ex.toString());
            result.setNeedRetry(true);
        }
        return result;
    }

    private static Optional<Boolean> statusFinally(int status) {
        Optional<Boolean> finalObj;
        if (status == 1) {
            finalObj = Optional.of(true);
        } else if (status == 2) {
            finalObj = Optional.of(false);
        } else {
            finalObj = Optional.absent();
        }
        return finalObj;
    }
}
