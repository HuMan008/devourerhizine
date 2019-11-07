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

import com.petroun.devourerhizine.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CNPC {

    private Dispatcher dispatcher;

    private CnpcProvidable provider;

    private static Logger logger = LoggerFactory.getLogger(CNPC.class);

    private Integer amount;
    private Director director;

    public CNPC(Integer amount, Director director) {
        this.amount = amount;
        this.director = director;
        dispatcher = Application.getApplicationContext().getBean(Dispatcher.class);
        provider = dispatcher.provider(amount, director);
    }

    public CNPC(Integer providerId) {
        dispatcher = Application.getApplicationContext().getBean(Dispatcher.class);
        provider = dispatcher.provider(providerId);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public CnpcProvidable getProvider() {
        return provider;
    }


    public CnpcProviderResult charge(Director director, Long orderId, String card, Integer amount) {
        logger.info("charge provider:{}", provider.name());
        return provider.charge(director, orderId, card, amount);
    }

    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard){
        return provider.inquire(orderId, providerOrder, directorCard);
    }

}
