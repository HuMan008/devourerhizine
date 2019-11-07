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

import com.petroun.devourerhizine.provider.cnpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * http://www.jisuapi.com/api/fuelcardrecharge/
 */
@Component
public class Jisu implements CnpcProvidable {

    private static final String JisuName = "Jisu";
    private static final String CHINESE_NAME = "极速";

    private static final List<Director> AVAILABLE_DIRECTORS = new ArrayList<>();
    private static final List<Integer> SINOPEC_AMOUNTS = new ArrayList<>();

    @Autowired
    private JisuInvoker invoker;

    static {
        AVAILABLE_DIRECTORS.add(Director.SINOPEC);

        SINOPEC_AMOUNTS.add(100 * 100);
        SINOPEC_AMOUNTS.add(200 * 100);
        SINOPEC_AMOUNTS.add(500 * 100);
        SINOPEC_AMOUNTS.add(1000 * 100);
    }

    @Override
    public Integer id() {
        return ID.Jisu;
    }

    @Override
    public String name() {
        return JisuName;
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
        if (!Director.SINOPEC.getCode().equals(director.getCode())) {
            return null;
        }

        return invoker.create(orderId, card, JisuAmount(amount));
    }

    @Override
    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard) {
        return invoker.inquire(orderId, providerOrder);
    }

    @Override
    public Long balance() {
        return 0L;
    }

    private static Integer JisuAmount(Integer amount) {
        return amount / 100;
    }
}
