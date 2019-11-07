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

import cn.gotoil.bill.exception.BillException;
import com.petroun.devourerhizine.provider.cnpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Dingxin implements CnpcProvidable {

    @Autowired
    private DingxinInvoker dingxinInvoker;

    private static final String NAME = "Dingxin";
    private static final String CHINESE_NAME = "鼎信";
    //    private static final Logger logger = LoggerFactory.getLogger(Dingxin.class);
    private static final List<Director> AVAILABLE_DIRECTORS = new ArrayList<>();
    private static final List<Integer> SINOPEC_AMOUNTS = new ArrayList<>();
    private static final List<Integer> CNPC_AMOUNTS = new ArrayList<>();


    private static final Map<Integer, String> SinpoecSupportedAmountsProid = new HashMap<>();
    private static final Map<Integer, String> CnpcSupportedAmountsProid = new HashMap<>();

    static {
        AVAILABLE_DIRECTORS.add(Director.SINOPEC);
        AVAILABLE_DIRECTORS.add(Director.CNPC);

        SINOPEC_AMOUNTS.add(100 * 100);
        SINOPEC_AMOUNTS.add(20000);
        SINOPEC_AMOUNTS.add(500 * 100);
        SINOPEC_AMOUNTS.add(1000 * 100);

        CNPC_AMOUNTS.add(100 * 100);
        CNPC_AMOUNTS.add(20000);
        CNPC_AMOUNTS.add(500 * 100);
        CNPC_AMOUNTS.add(1000 * 100);


        SinpoecSupportedAmountsProid.put(10000, "100037");
        SinpoecSupportedAmountsProid.put(20000, "100004");
        SinpoecSupportedAmountsProid.put(50000, "100036");
        SinpoecSupportedAmountsProid.put(100000, "100039");

        CnpcSupportedAmountsProid.put(10000, "100013");
        CnpcSupportedAmountsProid.put(20000, "100015");
        CnpcSupportedAmountsProid.put(50000, "100047");
        CnpcSupportedAmountsProid.put(100000, "100012");
    }

    @Override
    public Integer id() {
        return ID.Dingxin;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String chineseName() {
        return CHINESE_NAME;
    }


    @Override
    public List<Director> availableDirectors() {
        return AVAILABLE_DIRECTORS;
    }

    @Override
    public List<Integer> availableAmounts(Director director) {
        return CnpcHelper.availableAmounts(SINOPEC_AMOUNTS, CNPC_AMOUNTS, director);
    }

    @Override
    public boolean supportedAmount(Integer amount, Director director) {
        return CnpcHelper.supportedAmount(amount, SINOPEC_AMOUNTS, CNPC_AMOUNTS, director);
    }

    @Override
    public boolean supportedDirector(Director director) {
        return Director.SINOPEC.getCode().equals(director.getCode())
                || Director.CNPC.getCode().equals(director.getCode());
    }

    @Override
    public CnpcProviderResult charge(Director director, Long orderId, String card, Integer amount) {
        String productid = proid(director, amount);
        if (StringUtils.isEmpty(productid)) {
            throw new BillException(1, "Unsupported amount");
        }
        return dingxinInvoker.charge(orderId, card, productid);
    }

    @Override
    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard) {
        return dingxinInvoker.inquire(orderId);
    }

    @Override
    public Long balance() {
        return dingxinInvoker.balance();
    }


    private static String proid(Director director, Integer amount) {

        if (director.getCode().equals(Director.CNPC.getCode())) {
            return CnpcSupportedAmountsProid.getOrDefault(amount, null);
        }

        if (director.getCode().equals(Director.SINOPEC.getCode())) {
            return SinpoecSupportedAmountsProid.getOrDefault(amount, null);
        }


        return null;
    }
}
