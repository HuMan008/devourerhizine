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


import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.provider.cnpc.*;
import com.petroun.devourerhizine.service.OptionService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHost;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class DingxinInvoker {
    @Autowired
    private OptionService optionService;

    private static Logger logger = LoggerFactory.getLogger(DingxinInvoker.class);

    private static String[] BalanceKeysOrd = {"uaccount", "timestamp"};
    private static String[] CharegeKeysOrd = {"orderid", "productid", "uaccount", "account", "timestamp"};
    private static String[] InquireKeysOrd = {"orderid", "uaccount"};

    private String secret() {
        return optionService.get(OptionKeys.DingxinSecret, "");
    }

    public Long balance() {

        if (false) {
            Unirest.setProxy(new HttpHost("201.201.201.173", 8888));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("uaccount", Configer.getAccount());
        params.put("timestamp", String.valueOf(Instant.now().toEpochMilli()));
        sign(params, BalanceKeysOrd);
        try {
            HttpRequest request = Unirest.get(Configer.getBalanceUrl()).headers(CnpcConfiger.getHeaders())
                    .queryString(params).getHttpRequest();
            HttpResponse<String> res = request.asString();
            String body = res.getBody();
            logger.info("{}", body);

            if (!CnpcHelper.isNormalHttpcode(res.getStatus())) {
                return 0L;
            }

            try {
                JsonNode jsonNode = new JsonNode(body);
                JSONObject root = jsonNode.getObject();
                if (root.has("code") && root.has("balance") && root.getInt("code") == 10010) {
                    Double d = root.getDouble("balance") * 100;
                    return d.longValue();
                } else {
                    logger.error("{}", body);
                }
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return 0L;
    }


    public CnpcProviderResult charge(Long orderId, String card, String proid) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderid", orderId);
        params.put("productid", proid);
        params.put("uaccount", Configer.getAccount());
        params.put("account", card);
        params.put("timestamp", String.valueOf(Instant.now().toEpochMilli()));
        sign(params, CharegeKeysOrd);

        CnpcProviderResult result = new CnpcProviderResult();
        result.setOrderId(orderId);
        CnpcProviderLog providerLog = result.getProviderLog();
        providerLog.setRequestAt(new Date());

        try {
            HttpResponse<String> res = CnpcHelper.httpGetInvoke(Configer.getRechargeUrl(), params, providerLog);
            String body = providerLog.getResponse();

            if (CnpcHelper.isNormalHttpcode(res.getStatus())) {
                try {
                    JsonNode jsonNode = new JsonNode(body);
                    JSONObject root = jsonNode.getObject();
                    chargeResultProcess(root, result);
                } catch (Exception ex) {
                    logger.info("{}", body);
                    logger.error("{}", ex);
                }
            } else {
                logger.error("{}", body);
                result.setNeedRetry(true);
            }
        } catch (Exception ex) {
            logger.error("{}", ex);
            result.getProviderLog().setHttpCode(0);
            result.getProviderLog().setResponse(ex.toString());
            result.setNeedRetry(true);
            result.setSubmitSuccess(false);  //处理了重复订单
        }

        return result;
    }


    public CnpcProviderInquireResult inquire(Long orderId) {

        Map<String, Object> params = new HashMap<>();
        params.put("orderid", orderId);
        params.put("uaccount", Configer.getAccount());
        sign(params, InquireKeysOrd);


        CnpcProviderInquireResult result = new CnpcProviderInquireResult();
        result.setOrderId(orderId);
        result.getProviderLog().setRequestAt(new Date());

        try {
            HttpResponse<String> res = CnpcHelper.httpPostInvoke(Configer.getInquireUrl(), params, result.getProviderLog());
            String body = result.getProviderLog().getResponse();
            if (CnpcHelper.isNormalHttpcode(res.getStatus())) {
                try {
                    JsonNode jsonNode = new JsonNode(body);
                    JSONObject root = jsonNode.getObject();

                    if (root.getInt("code") == 10010) {
                        result.setExist(true);
                        int state = root.getInt("state");
                        if (state == 1 || state == 2) {
                            result.setResult(Optional.of(state == 1));
                            if (state == 2) {
//                                result.setFailureReason(root.getString("message"));
                            }
                        } else {
                            result.setNeedRetry(true);
                        }
                    }
                } catch (Exception ex) {
                    logger.info("{}", body);
                    logger.error("{}", ex);
                }
            } else {
                logger.error("{}", body);
            }
        } catch (Exception ex) {
            logger.error("{}", ex);
            result.getProviderLog().setHttpCode(0);
            result.getProviderLog().setResponse(ex.toString());
            result.setNeedRetry(true);
        }

        return result;
    }

    private void chargeResultProcess(JSONObject root, CnpcProviderResult result) {
        int state = root.getInt("code");
        if (state == 10010 || state == 1009) {
            result.setSubmitSuccess(true);
        } else {
            result.setFailureReason(root.getString("message"));
        }
    }


    //    @SuppressWarnings("all")
    public void sign(Map<String, Object> params, String[] keysOrd) {
        StringBuilder payload = new StringBuilder(params.size() * 15);
//        ArrayList<String> keylist = new ArrayList();
//        keylist.addAll(params.keySet());
//        keylist.sort(Comparator.naturalOrder());
        for (String key : keysOrd) {
            payload.append(key);
            payload.append(params.get(key));
        }

        payload.append(secret());
        String s = DigestUtils.md5Hex(payload.toString());

//        logger.info("payload:{} sign:{}", payload.toString(), s);

        params.put("sign", s);
    }


}
