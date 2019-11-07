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

import com.petroun.devourerhizine.provider.cnpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Qianxing implements CnpcProvidable {

    private static final String NAME = "Qianxing";
    private static final String CHINESE_NAME = "千行";

    private static final List<Director> AVAILABLE_DIRECTORS = new ArrayList<>();
    private static final List<Integer> SINOPEC_AMOUNTS = new ArrayList<>();
    private static final List<Integer> CNPC_AMOUNTS = new ArrayList<>();


    static {
        SINOPEC_AMOUNTS.add(10000);
//        SINOPEC_AMOUNTS.add(20000);
        SINOPEC_AMOUNTS.add(50000);
        SINOPEC_AMOUNTS.add(100000);

        CNPC_AMOUNTS.add(10000);
        CNPC_AMOUNTS.add(50000);
        CNPC_AMOUNTS.add(20000);
        CNPC_AMOUNTS.add(100000);

        AVAILABLE_DIRECTORS.add(Director.SINOPEC);
        AVAILABLE_DIRECTORS.add(Director.CNPC);
    }

    @Autowired
    private QianxingInvoker invoker;

    @Override
    public Integer id() {
        return ID.Qianxing;
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
        return invoker.create(director, orderId, card, QianxingAmount(amount));
    }

    @Override
    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard) {
        return invoker.inquire(orderId);
    }

    @Override
    public Long balance() {
        return invoker.balance();
    }

    private static Integer QianxingAmount(Integer amount) {
        return amount / 100;
    }

}
