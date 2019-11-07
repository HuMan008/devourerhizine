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
public class Xunyin implements CnpcProvidable {

    private static final String NAME = "Xunyin";
    private static final String CHINESE_NAME = "迅银";

//    private static final Logger logger = LoggerFactory.getLogger(Xunyin.class);

    private static final List<Director> AVAILABLE_DIRECTORS = new ArrayList<>();
    private static final List<Integer> SINOPEC_AMOUNTS = new ArrayList<>();
    private static final Map<Integer, String> SinpoecSupportedAmountsProid = new HashMap<>();
    static {
        AVAILABLE_DIRECTORS.add(Director.SINOPEC);

//        SINOPEC_AMOUNTS.add(10000);
//        SINOPEC_AMOUNTS.add(20000);

        SINOPEC_AMOUNTS.add(500 * 100);
        SINOPEC_AMOUNTS.add(1000 * 100);


//        SinpoecSupportedAmountsProid.put(10000, "800936");
        SinpoecSupportedAmountsProid.put(500 * 100, "800938");
        SinpoecSupportedAmountsProid.put(1000 * 100, "800939");
    }

    @Autowired
    private XunyinInovker xunyinInovker;

    @Override
    public Integer id() {
        return ID.Xunyin;
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
        if (!Director.SINOPEC.getCode().equals(director.getCode())) {
            return null;
        }

        return SINOPEC_AMOUNTS;
    }

    @Override
    public boolean supportedAmount(Integer amount, Director director) {
        if (!Director.SINOPEC.getCode().equals(director.getCode())) {
            return false;
        }
        return SINOPEC_AMOUNTS.contains(amount);
    }

    @Override
    public boolean supportedDirector(Director director) {
        return Director.SINOPEC.getCode().equals(director.getCode());
    }


    @Override
    public CnpcProviderResult charge(Director director, Long orderId, String card, Integer amount) {

        String proid = proid(director, amount);
        if (StringUtils.isEmpty(proid)) {
            throw new BillException(CnpcProviderError.UnableSupportedThisAmount);
        }

        return xunyinInovker.charge(orderId, card, proid);
    }

    @Override
    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard) {
        return xunyinInovker.inquire(orderId);
    }

    @Override
    public Long balance() {
        return xunyinInovker.balance();
    }

    private String proid(Director director, Integer amount) {
        if (!director.getCode().equals(Director.SINOPEC.getCode())) {
            return null;
        }
        return SinpoecSupportedAmountsProid.getOrDefault(amount, null);
    }
}

