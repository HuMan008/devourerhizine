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

package com.petroun.devourerhizine.provider.cnpc.jiaofei100;


import com.petroun.devourerhizine.provider.cnpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Jiaofei100 implements CnpcProvidable {

    private static final Logger logger = LoggerFactory.getLogger(Jiaofei100.class);
    private static final String NAME = "Jiaofei100";
    private static final String CHINESE_NAME = "缴费100";
    private static final List<Director> AVAILABLE_DIRECTORS = new ArrayList<>();
    private static final List<Integer> SINOPEC_AMOUNTS = new ArrayList<>();

    static {
        AVAILABLE_DIRECTORS.add(Director.SINOPEC);
        //TODO
        if (true) {
            SINOPEC_AMOUNTS.add(100 * 100);
            SINOPEC_AMOUNTS.add(200 * 100);
            SINOPEC_AMOUNTS.add(500 * 100);
            SINOPEC_AMOUNTS.add(1000 * 100);
        }
    }

    @Autowired
    private Jiaofei100Invoker invoker;

    @Override
    public Integer id() {
        return ID.Jiaofei100;
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
        return null;
    }

    @Override
    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard) {
        return null;
    }

    @Override
    public Long balance() {
        return null;
    }
}
