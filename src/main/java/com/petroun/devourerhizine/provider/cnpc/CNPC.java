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

import com.google.common.base.Optional;
import com.petroun.devourerhizine.Application;
import com.petroun.devourerhizine.config.CnpcConfig;
import com.petroun.devourerhizine.model.enums.CnpcOrderInvokeLogType;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class CNPC {

    private Dispatcher dispatcher;

    private CnpcProvidable provider;

    private CnpcConfig cnpcConfig;

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
        if (cnpcConfig != null && cnpcConfig.isMock()) {
            CnpcProviderResult mockResult = new CnpcProviderResult();
            mockResult.setOrderId(orderId);
            mockResult.setProviderOrder("MOCK_" + RandomUtils.nextInt());
            Boolean s = RandomUtils.nextBoolean();
            mockResult.setSubmitSuccess(s);
            mockResult.setProviderLog(mockProviderLog());

            if (!s) {
                mockResult.setNeedRetry(RandomUtils.nextBoolean());
                mockResult.setFailureReason("MOCK FAILURE REASON");
            }
            return mockResult;
        }

        logger.info("charge provider:{}", provider.name());
        return provider.charge(director, orderId, card, amount);
    }

    public CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard) {
        if (cnpcConfig != null && cnpcConfig.isMock()) {
            CnpcProviderInquireResult mockResult = new CnpcProviderInquireResult();
            mockResult.setDirectorOrder("MOCK DIRECT ORDER " + RandomUtils.nextInt());

            mockResult.setProviderOrder("MOCK " + RandomUtils.nextInt());
            Boolean exist = RandomUtils.nextInt(0, 100) > 5;
            mockResult.setExist(exist);
            mockResult.setOrderId(orderId);
            mockResult.setProviderLog(mockProviderLog());
            if (exist) {
                int r = RandomUtils.nextInt(0, 100);
                if (r < 20) {
                    mockResult.setResult(Optional.of(true));
                } else if (r > 80) {
                    mockResult.setResult(Optional.of(false));
                    mockResult.setFailureReason("MOCK FAILURE REASON");
                } else {
                    mockResult.setNeedRetry(true);
                }
            } else {
                mockResult.setNeedRetry(RandomUtils.nextBoolean());
            }


            return mockResult;
        }
        return provider.inquire(orderId, providerOrder, directorCard);
    }

    public CnpcConfig getCnpcConfig() {
        return cnpcConfig;
    }

    public void setCnpcConfig(CnpcConfig cnpcConfig) {
        this.cnpcConfig = cnpcConfig;
    }

    private static CnpcProviderLog mockProviderLog() {
        CnpcProviderLog providerLog = new CnpcProviderLog();
        providerLog.setHttpCode(200);
        providerLog.setRequest("MOCK REQUEST");
        providerLog.setRequestAt(new Date());
        providerLog.setResponse("MOCK RESPONSE");
        providerLog.setResponseAt(new Date());
        return providerLog;
    }
}
