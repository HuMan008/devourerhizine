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

import cn.gotoil.bill.tools.date.DateUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.petroun.devourerhizine.provider.cnpc.*;
import com.petroun.devourerhizine.provider.cnpc.xunyin.entity.XunyinBalance;
import com.petroun.devourerhizine.provider.cnpc.xunyin.entity.XunyinCreate;
import com.petroun.devourerhizine.provider.cnpc.xunyin.entity.XunyinInquire;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class XunyinInovker {

    private static Logger logger = LoggerFactory.getLogger(XunyinInovker.class);

    private XmlMapper xmlMapper = new XmlMapper();

    @Autowired
    private XunyinConfiger configer;

    @PostConstruct
    public void construct() {
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CnpcProviderResult charge(Long orderId, String card, String proid) {
        Map<String, Object> params = chargeParamBuilder(orderId, card, proid);
        chargeSign(params);

        CnpcProviderResult result = new CnpcProviderResult();
        result.setOrderId(orderId);
        CnpcProviderLog providerLog = result.getProviderLog();
        providerLog.setRequestAt(new Date());
        try {
//            HttpRequest request = Unirest.post(XunyinConfiger.getThroughUrl()).headers(CnpcConfiger.getHeaders()).
//                    fields(params).getHttpRequest();
//            providerLog.setRequest(ObjectHelper.jsonString(params));
//            HttpResponse<String> res = request.asString();
//            providerLog.setHttpCode(res.getStatus());
//            providerLog.setResponseAt(new Date());
//            String body = res.getBody();
//            providerLog.setResponse(body);
//            logger.info("{}", body);

            HttpResponse<String> res = CnpcHelper.httpPostInvoke(XunyinConfiger.getThroughUrl(), params, providerLog);
            String body = providerLog.getResponse();

            if (res.getStatus() >= 200 && res.getStatus() < 300) {
                try {
                    XunyinCreate create = xmlMapper.readValue(body, XunyinCreate.class);
                    logger.info("Create:{}", create);
                    XunyinHelper.createResultProcess(result, create);
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
            result.setSubmitSuccess(true);
        }

        return result;
    }

    public CnpcProviderInquireResult inquire(Long orderId) {

        Map<String, Object> params = new HashMap<>();
        params.put("cpid", XunyinConfiger.getCpid());
        params.put("OrderID", orderId);
        params.put("sign", DigestUtils.md5Hex(XunyinConfiger.getCpid() + orderId.toString() + configer.key()));

        CnpcProviderInquireResult result = new CnpcProviderInquireResult();
        result.setOrderId(orderId);
        CnpcProviderLog providerLog = result.getProviderLog();
        providerLog.setRequestAt(new Date());


        try {
//            HttpRequest request = Unirest.post(XunyinConfiger.getInquireUrl()).headers(CnpcConfiger.getHeaders())
//                    .fields(params).getHttpRequest();
//            providerLog.setRequest(ObjectHelper.jsonString(params));
//            HttpResponse<String> res = request.asString();
//            providerLog.setHttpCode(res.getStatus());
//            providerLog.setResponseAt(new Date());
//            String body = res.getBody();
//            providerLog.setResponse(body);

            HttpResponse<String> res = CnpcHelper.httpPostInvoke(XunyinConfiger.getInquireUrl(), params, providerLog);
            String body = providerLog.getResponse();

            if (CnpcHelper.isNormalHttpcode(res.getStatus())) {
                try {
                    XunyinInquire inquire = xmlMapper.readValue(body, XunyinInquire.class);
                    logger.info("{}", inquire);
                    XunyinHelper.inquireResultProcess(result, inquire);
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

    private Map<String, Object> chargeParamBuilder(Long orderId, String card, String proid) {
        Map<String, Object> params = new HashMap<>();
        params.put("cpid", XunyinConfiger.getCpid());
        params.put("gamegoodid", proid);
        params.put("createtime", DateUtils.fetchSimpleDateFormatter("yyyyMMddHHmmss").format(new Date()));
        params.put("account", card);
        params.put("orderid", orderId.toString());
        params.put("buyerIp", "172.16.0.1");
        params.put("returnurl", "");
        params.put("buynum", "1");

        chargeSign(params);

        return params;
    }

    private void chargeSign(Map<String, Object> params) {
        //cpid="+cpid+"&gamegoodid="+gamegoodid+"&createtime="+createtime+"&account="+account+"&orderid="+orderid+"&buynum="+buynum+key
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cpid=");
        stringBuilder.append(XunyinConfiger.getCpid());
        stringBuilder.append("&gamegoodid=");
        stringBuilder.append(params.get("gamegoodid"));
        stringBuilder.append("&createtime=");
        stringBuilder.append(params.get("createtime"));
        stringBuilder.append("&account=");
        stringBuilder.append(params.get("account"));
        stringBuilder.append("&orderid=");
        stringBuilder.append(params.get("orderid"));
        stringBuilder.append("&buynum=");
        stringBuilder.append(params.get("buynum"));
        stringBuilder.append(configer.key());

        params.put("sign", DigestUtils.md5Hex(stringBuilder.toString()));
    }

    public Long balance() {
        Map<String, Object> params = new HashMap<>();
        params.put("cpid", XunyinConfiger.getCpid());
        params.put("sign", DigestUtils.md5Hex(XunyinConfiger.getCpid() + configer.key()));
        try {
            HttpRequest request = Unirest.post(XunyinConfiger.getBalanceUrl()).headers(CnpcConfiger.getHeaders()).
                    fields(params).getHttpRequest();
            HttpResponse<String> res = request.asString();
            String body = res.getBody();
            //  logger.info("{}", body);
            // <?xml version="1.0" encoding="utf-8" ?>
            // <merchant>
            // <code>0000</code>   <cpid>508</cpid>   <name>国通石油</name>   <balance>1000.00</balance>   <state>状态正常</state></merchant>
            if (CnpcHelper.isNormalHttpcode(res.getStatus())) {
                try {
//                    logger.info("{}", body);
                    XunyinBalance balance = xmlMapper.readValue(body, XunyinBalance.class);
                    Double f = balance.getBalance() * 100;
                    return f.longValue();
                } catch (Exception ex) {
                    logger.error("{}", ex);
                }
            } else {
                logger.error("{}", body);
            }
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return 0L;
    }
}
