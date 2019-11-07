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

import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.provider.cnpc.*;
import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.petroun.devourerhizine.service.OptionService;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
@Component
public class JuheInvoker {

    @Autowired
    private OptionService optionService;

    private static final Logger logger = LoggerFactory.getLogger(JuheInvoker.class);

    // 0位:retry  1位: success
    private static final Map<Integer, Integer> THROUGHT_ERCODES = new HashMap<>();
    private static final int THROUGHT_ERCODES_BIT_RETRY = 0;
    private static final int THROUGHT_ERCODES_BIT_SUCCE = 1;

    static {
        // 1位: success 0位:retry
        THROUGHT_ERCODES.put(208710, 0b000010); // 208710 重复的订单号（需要自行进行二次确认，是否进行失败处理）
        THROUGHT_ERCODES.put(208707, 0b000001); // 208707 账户余额不足
    }

    /**
     * https://www.juhe.cn/docs/api/id/85/aid/577
     *
     * @return
     */
    public Long balance() {
        String current = String.valueOf(Instant.now().getEpochSecond());
        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", current);
        params.put("key", Configer.getBalanceKey());
        String sign = openid() + Configer.getBalanceKey() + current;
        sign = DigestUtils.md5Hex(sign);
        params.put("sign", sign);
        try {
            HttpRequest request = Unirest.get(Configer.getBalanceUrl()).headers(CnpcConfiger.getHeaders()).queryString(params);
            HttpResponse<String> res = request.asString();
            String body = res.getBody();
            logger.info("{}", body);
            if (res.getStatus() >= 200 && res.getStatus() < 300) {
                try {
                    JsonNode jsonNode = new JsonNode(body);
                    JSONObject root = jsonNode.getObject();
                    double m = root.getJSONObject("result").getDouble("money");
                    return new Double(m * 100).longValue();
                } catch (Exception ex) {
                    logger.error("{}", ex);
                }
            } else {
                logger.error("{}", body);
            }
        } catch (Exception ex) {

        }
        return 0L;
    }

    /**
     * @param orderId
     * @param card
     * @param proid
     * @return
     */
    public CnpcProviderResult charge(Long orderId, String card, String proid, String chargeType, String amount) {
        String current = String.valueOf(Instant.now().getEpochSecond());
        Map<String, Object> params = commonParams(orderId, current);
        if ("1".equals(chargeType)) {
            params.put("cardnum", "1");
        } else {
            params.put("cardnum", amount);
        }
        params.put("proid", proid);
        params.put("game_userid", card);
        params.put("gasCardTel", "18900000000");
        params.put("chargeType", chargeType);  //	加油卡类型 （1:中石化、2:中石油；默认为1)

        //md5(OpenID+key+proid+cardnum+game_userid+orderid)
        String sign = openid() + Configer.getKey() + proid + params.get("cardnum")
                + card + orderId.toString();
        sign = DigestUtils.md5Hex(sign);
        params.put("sign", sign);

        CnpcProviderResult result = new CnpcProviderResult();
        CnpcProviderLog providerLog = result.getProviderLog();

        result.setOrderId(orderId);
        providerLog.setRequestAt(new Date());

        try {
//            HttpRequest request = Unirest.get(Configer.getThroughUrl()).headers(CnpcConfiger.getHeaders()).queryString(params);
//            providerLog.setRequest(ObjectHelper.jsonString(params));
//            HttpResponse<String> res = request.asString();
//            providerLog.setHttpCode(res.getStatus());
//            providerLog.setResponseAt(new Date());
//            String body = res.getBody();
//            providerLog.setResponse(body);

            HttpResponse<String> res = CnpcHelper.httpGetInvoke(Configer.getThroughUrl(), params, providerLog);
            String body = providerLog.getResponse();

            if (res.getStatus() >= 200 && res.getStatus() < 300) {
                try {
                    JsonNode jsonNode = new JsonNode(body);
                    JSONObject root = jsonNode.getObject();
                    int ercode = root.getInt("error_code");
                    result.setSubmitSuccess(ercode == 0);
                    if (ercode == 0) {
                        String providerOrder = root.getJSONObject("result").getString("sporder_id");
                        result.setProviderOrder(providerOrder);
                    } else if (ercode == 10014) {
                        // 10014 系统内部异常
                        result.setSubmitSuccess(true);
                    } else if (ercode == 208710) {
                        // 208710 订单重复
                        result.setSubmitSuccess(true);
                    } else if (ercode == 208707) {
                        // 余额不足
                        result.setFailureReason("系统错误:PC02_01");
                    } else if (ercode == 208709) {
                        //该商品暂不可用
                        result.setFailureReason("系统PROXY错误");
                    } else {
                        result.setFailureReason(root.getString("reason"));
                        logger.error("{}", body);
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
            result.setSubmitSuccess(true);

            CnpcProviderInquireResult inquireResult = inquire(orderId);
            if (inquireResult.isExist()) {
                result.setSubmitSuccess(true);
                result.setProviderOrder(inquireResult.getProviderOrder());
            } else {
                result.setSubmitSuccess(false);
            }
        }

        return result;
    }

    /**
     * @param orderId
     * @return
     */
    public CnpcProviderInquireResult inquire(Long orderId) {
        String current = String.valueOf(Instant.now().getEpochSecond());
        Map<String, Object> params = commonParams(orderId, current);
        String sign = openid() + Configer.getKey() + orderId.toString() + current;
        sign = DigestUtils.md5Hex(sign);
        params.put("sign", sign);

        CnpcProviderInquireResult result = new CnpcProviderInquireResult();
        result.setOrderId(orderId);
        CnpcProviderLog providerLog = result.getProviderLog();
        providerLog.setRequestAt(new Date());

        try {
//            HttpRequest request = Unirest.get(Configer.getInquireUrl()).headers(CnpcConfiger.getHeaders()).queryString(params);
//            providerLog.setRequest(ObjectHelper.jsonString(params));
//            HttpResponse<String> res = request.asString();
//            providerLog.setHttpCode(res.getStatus());
//            providerLog.setResponseAt(new Date());
//            String body = res.getBody();
//            providerLog.setResponse(body);


            HttpResponse<String> res = CnpcHelper.httpGetInvoke(Configer.getInquireUrl(), params, providerLog);
            String body = providerLog.getResponse();

            if (res.getStatus() >= 200 && res.getStatus() < 300) {
                try {
                    JsonNode jsonNode = new JsonNode(body);
                    JSONObject root = jsonNode.getObject();
                    int ercode = root.getInt("error_code");
                    result.setExist(ercode == 0);
                    if (ercode == 0) {
                        JSONObject resultLevel = root.getJSONObject("result");
                        result.setProviderOrder(resultLevel.getString("sporder_id"));
                        result.setResult(statusFinally(resultLevel.getInt("game_state")));

                        if (result.getResult().isPresent() && !result.getResult().get()) {
                            result.setFailureReason("系统错误:PC02_03:" + resultLevel.getString("err_msg"));
                        }

                        result.setNeedRetry(true);
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

    /**
     * @param providerResult
     * @param ercode
     */
    @SuppressWarnings("unused")
    private void judgeThroughRetryAndResult(CnpcProviderResult providerResult, int ercode) {
        Integer r = THROUGHT_ERCODES.getOrDefault(ercode, null);
        if (r == null) {
            logger.error("Unkown error code.{}", ercode);
        } else {
            int rint = r.intValue();
            //0位:retry  1位: success
            providerResult.setNeedRetry((rint >> THROUGHT_ERCODES_BIT_RETRY & 0x01) == 0x01);
            providerResult.setSubmitSuccess((rint >> THROUGHT_ERCODES_BIT_SUCCE & 0x01) == 0x01);
        }
    }

    /**
     * @param orderId
     * @param current
     * @return
     */
    private Map<String, Object> commonParams(Long orderId, String current) {
        if (null == current) {
            current = String.valueOf(Instant.now().getEpochSecond());
        }

        Map<String, Object> params = new HashMap<>();
        params.put("orderid", orderId.toString());
        params.put("timestamp", current);
        params.put("key", Configer.getKey());

        return params;
    }

    /**
     * @return
     */
    private String openid() {
        return optionService.get(OptionKeys.JuheOpenID, "");
    }

    /**
     * @param status
     * @return
     */
    private static Optional<Boolean> statusFinally(int status) {
        Optional<Boolean> finalObj;
        if (status == 1) {
            finalObj = Optional.of(true);
        } else if (status == 9) {
            finalObj = Optional.of(false);
        } else {
            finalObj = Optional.absent();
        }
        return finalObj;
    }
}
