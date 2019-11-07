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

import cn.gotoil.bill.tools.date.DateUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.provider.cnpc.*;
import com.petroun.devourerhizine.provider.cnpc.qianxing.entity.BalanceResponse;
import com.petroun.devourerhizine.provider.cnpc.qianxing.entity.CreateResponse;
import com.petroun.devourerhizine.provider.cnpc.qianxing.entity.InquireResponse;
import com.petroun.devourerhizine.service.OptionService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class QianxingInvoker {

    private static Logger logger = LoggerFactory.getLogger(QianxingInvoker.class);

    private static final String APIBASE = "http://jiayouapi.18jiayou.com:18880";
    private static final String CREATE_URL = APIBASE + "/order/request";
    private static final String INQUIRE_URL = APIBASE + "/order/query";
    private static final String CARD_URL = APIBASE + "/archive/query";
    private static final String BALANCE_URL = APIBASE + "/balance/query";

    private XmlMapper xmlMapper = new XmlMapper();

    private static final boolean debug = false;

    @Autowired
    private OptionService optionService;

    @PostConstruct
    public void construct() {
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String coopId() {
        if (debug) {
            return "gtjyk";
        }

        return optionService.get(OptionKeys.QianxingAccount);
    }

    private String secret() {
        if (debug) {
            return "c3f071c35ef24a7eb1f7296ff4ec0b4b";
        }

        return optionService.get(OptionKeys.QianxingSecret);
    }

    /**
     * @param orderId
     * @param card
     * @param amount  单位元！！！！
     * @return
     */
    public CnpcProviderResult create(Director director, Long orderId, String card, Integer amount) {
        Map<String, Object> params = createParams(director, orderId, card, amount);
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
                    CreateResponse createResponse = xmlMapper.readValue(body, CreateResponse.class);
                    if (QXHelper.createSuccess(createResponse)) {
                        result.setSubmitSuccess(true);
                    } else {
                        result.setFailureReason("系统错误:PC02_04:" + createResponse.getFailedReason());
                    }
                } catch (Exception ex) {
                    logger.error("{}", ex);
                }
            } else {
                logger.error("{}", body);
            }
        } catch (Exception ex) {
            providerLog.setHttpCode(0);
            providerLog.setResponse(ex.toString());
            result.setNeedRetry(true);
            result.setSubmitSuccess(false); //调用了重复查询的
        }

        return result;
    }


    public CnpcProviderInquireResult inquire(Long orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("coopId", coopId());
        params.put("coopOrderNo", orderId);
        sign(params);

        CnpcProviderInquireResult result = new CnpcProviderInquireResult();
        result.setOrderId(orderId);
        CnpcProviderLog providerLog = result.getProviderLog();
        providerLog.setRequestAt(new Date());

        try {

            HttpResponse<String> res = CnpcHelper.httpGetInvoke(INQUIRE_URL, params, providerLog);
            String body = providerLog.getResponse();

            if (CnpcHelper.isNormalHttpcode(res.getStatus())) {
                try {
                    InquireResponse inquireResponse = xmlMapper.readValue(body, InquireResponse.class);
                    QXHelper.processInquireResult(result, inquireResponse);
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


    public void card(String card) {
        Map<String, Object> params = new HashMap<>();
        params.put("coopId", coopId());
        params.put("cardNo", card);
        sign(params);
        try {
            HttpRequest request = Unirest.get(CARD_URL).headers(CnpcConfiger.getHeaders()).queryString(params);
            HttpResponse<String> res = request.asString();
            String body = res.getBody();
            logger.info(body);
        } catch (Exception ex) {
            logger.info("{}", ex);
        }
    }

    public Long balance() {
        Map<String, Object> params = new HashMap<>();
        params.put("coopId", coopId());
        sign(params);
        try {
            HttpRequest request = Unirest.get(BALANCE_URL).headers(CnpcConfiger.getHeaders()).queryString(params);
            HttpResponse<String> res = request.asString();
            String body = res.getBody();
            BalanceResponse balanceResponse = xmlMapper.readValue(body, BalanceResponse.class);
            logger.info("{}", body);
            Double d = balanceResponse.getBalance() * 100;
            return d.longValue();
        } catch (Exception ex) {
            logger.info("{}", ex);
        }

        return 0L;
    }


    private Map<String, Object> createParams(Director director, Long orderId, String card, Integer amount) {
        Map<String, Object> params = new HashMap<>();
        params.put("coopId", coopId());
        params.put("orderNo", orderId);
        params.put("productStandard", amount);
        params.put("orderNum", "1");
        params.put("totalStandard", amount);
        params.put("rechargeAccount", card);
        params.put("businessType", "11");
        if (Director.SINOPEC.getCode().equals(director.getCode())) {
            params.put("carrierNo", "ZSH");
        } else if (Director.CNPC.getCode().equals(director.getCode())) {
            params.put("carrierNo", "ZSY");
        }

        sign(params);

        return params;
    }


    private void sign(Map<String, Object> params) {
        SimpleDateFormat dateFormat = DateUtils.fetchSimpleDateFormatter("yyyyMMddHHmmss");
        String time = dateFormat.format(new Date());
        params.put("timestamp", time);

        List<String> keys = new ArrayList<>();
        keys.addAll(params.keySet());
        keys.sort(String::compareTo);

        String secret = secret();
        StringBuilder stringBuilder = new StringBuilder((keys.size() + 2) * 24);
        stringBuilder.append(secret);
        for (String key : keys) {
            stringBuilder.append(key);
            stringBuilder.append(params.get(key).toString());
        }
        stringBuilder.append(secret);
        String sign = DigestUtils.md5Hex(stringBuilder.toString());
        params.put("sign", sign);
    }


}
