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

package com.petroun.devourerhizine.provider.cnpc;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.tools.ObjectHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CnpcHelper {

    public static boolean isNormalHttpcode(int code) {
        return code >= 200 && code < 300;
    }

    public static Byte directorFromDirectorCard(String card) {
        if (card.length() == 19 && card.startsWith("100011")) {
            return new Byte(Director.SINOPEC.getCode().byteValue());
        }

        if (card.length() == 16 && card.startsWith("90")) {
            return new Byte(Director.CNPC.getCode().byteValue());
        }

        throw new BillException(8000, "Unknow director card");
    }

    /**
     * @param sinopec
     * @param cnpc
     * @param director
     * @return
     */
    public static List<Integer> availableAmounts(List<Integer> sinopec, List<Integer> cnpc, Director director) {
        if (Director.SINOPEC.getCode().equals(director.getCode())) {
            return sinopec;
        }

        if (Director.CNPC.getCode().equals(director.getCode())) {
            return cnpc;
        }

        return null;
    }

    /**
     * @param amount
     * @param sinopec
     * @param cnpc
     * @param director
     * @return
     */
    public static boolean supportedAmount(Integer amount, List<Integer> sinopec, List<Integer> cnpc, Director director) {
        if (Director.SINOPEC.getCode().equals(director.getCode())) {
            return sinopec.contains(amount);
        }

        if (Director.CNPC.getCode().equals(director.getCode())) {
            return cnpc.contains(amount);
        }

        return false;
    }


    /**
     *
     * @param request
     * @param params
     * @param providerLog
     * @return
     * @throws Exception
     */
    private static HttpResponse<String> httpInvokeDo(HttpRequest request, Map<String, Object> params, CnpcProviderLog providerLog)
            throws Exception {
        providerLog.setRequest(ObjectHelper.jsonString(params));
        HttpResponse<String> res = request.asString();
        providerLog.setHttpCode(res.getStatus());
        providerLog.setResponseAt(new Date());
        String body = res.getBody();
        providerLog.setResponse(body);

        return res;
    }

    /**
     * @param url
     * @param params
     * @param providerLog
     * @return
     * @throws Exception
     */
    public static HttpResponse<String> httpGetInvoke(String url, Map<String, Object> params, CnpcProviderLog providerLog)
            throws Exception {
        HttpRequest request = Unirest.get(url).headers(CnpcConfiger.getHeaders()).queryString(params);
        return httpInvokeDo(request, params, providerLog);
    }

    /**
     * @param url
     * @param params
     * @param providerLog
     * @return
     * @throws Exception
     */
    public static HttpResponse<String> httpPostInvoke(String url, Map<String, Object> params, CnpcProviderLog providerLog)
            throws Exception {
        HttpRequest request = Unirest.post(url).headers(CnpcConfiger.getHeaders()).
                fields(params).getHttpRequest();
        return httpInvokeDo(request, params, providerLog);
    }
}
