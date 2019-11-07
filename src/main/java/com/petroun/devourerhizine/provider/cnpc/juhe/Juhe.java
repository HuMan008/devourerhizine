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

import cn.gotoil.bill.exception.BillException;
import com.petroun.devourerhizine.provider.cnpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * https://www.juhe.cn/docs/api/id/87
 */

@Component
public class Juhe implements CnpcProvidable {

    private static final String JuheName = "Juhe";
    private static final String CHINESE_NAME = "聚合";

    private static final List<Director> SupportedDirectors = new ArrayList<>();

    private static final List<Integer> SinpoecSupportedAmounts = new ArrayList<>();
    private static final List<Integer> CNPCSupportedAmounts = new ArrayList<>();

    private static final Map<Integer, String> SinpoecSupportedAmountsProid = new HashMap<>();
    private static final Map<Integer, String> CnpcSupportedAmountsProid = new HashMap<>();


    @Autowired
    private JuheInvoker juheInvoker;


    static {
        SupportedDirectors.add(Director.SINOPEC);
        SupportedDirectors.add(Director.CNPC);

        SinpoecSupportedAmounts.add(10000);
        SinpoecSupportedAmounts.add(20000);
        SinpoecSupportedAmounts.add(50000);
        SinpoecSupportedAmounts.add(100000);

        SinpoecSupportedAmountsProid.put(10000, "10001");
        SinpoecSupportedAmountsProid.put(20000, "10002");
        SinpoecSupportedAmountsProid.put(50000, "10003");
        SinpoecSupportedAmountsProid.put(100000, "10004");


        CNPCSupportedAmounts.add(10000);
        CNPCSupportedAmounts.add(20000);
        CNPCSupportedAmounts.add(50000);
        CNPCSupportedAmounts.add(100000);


        CnpcSupportedAmountsProid.put(10000, "10008");
        CnpcSupportedAmountsProid.put(20000, "10008");
        CnpcSupportedAmountsProid.put(50000, "10008");
        CnpcSupportedAmountsProid.put(100000, "10008");
    }

    @Override
    public Integer id() {
        return ID.Juhe;
    }

    @Override
    public String name() {
        return JuheName;
    }

    @Override
    public String chineseName() {
        return CHINESE_NAME;
    }

    @Override
    public List<Director> availableDirectors() {
        return SupportedDirectors;
    }

    @Override
    public List<Integer> availableAmounts(Director director) {
        if (director.getCode().equals(Director.SINOPEC.getCode())) {
            return SinpoecSupportedAmounts;
        }

        if (director.getCode().equals(Director.CNPC.getCode())) {
            return CNPCSupportedAmounts;
        }

        return null;
    }

    @Override
    public boolean supportedAmount(Integer amount, Director director) {
        if (director.getCode().equals(Director.SINOPEC.getCode())) {
            return SinpoecSupportedAmounts.contains(amount);
        }

        if (director.getCode().equals(Director.CNPC.getCode())) {
            return CNPCSupportedAmounts.contains(amount);
        }

        return false;
    }

    @Override
    public boolean supportedDirector(Director director) {
        return director.getCode().equals(Director.SINOPEC.getCode())
                || director.getCode().equals(Director.CNPC.getCode());
    }

    @Override
    public CnpcProviderResult charge(Director director, Long orderId, String card, Integer amount) {
        String proid = proid(director, amount);
        if (StringUtils.isEmpty(proid)) {
            throw new BillException(CnpcProviderError.UnableSupportedThisAmount);
        }


        //	加油卡类型 （1:中石化、2:中石油；默认为1)
        String charType = "1";
        String toamount = String.valueOf(amount / 100);
        if (director.getCode().equals(Director.CNPC.getCode())) {
            charType = "2";
        }

        return juheInvoker.charge(orderId, card, proid, charType, toamount);
    }

    /**
     * @param orderId
     * @param providerOrder
     * @return
     */
    @Override
    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard) {
        return juheInvoker.inquire(orderId);
    }

    /**
     * @param director
     * @param amount
     * @return
     */
    private static String proid(Director director, Integer amount) {
        if (director.getCode().equals(Director.SINOPEC.getCode())) {
            return SinpoecSupportedAmountsProid.getOrDefault(amount, null);
        }

        if (director.getCode().equals(Director.CNPC.getCode())) {
            return CnpcSupportedAmountsProid.getOrDefault(amount, null);
        }

        return null;
    }

    @Override
    public Long balance() {
        return juheInvoker.balance();
    }
}
