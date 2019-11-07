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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class BalanceMaintainer {

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private static final String CnpcBalanceMaintainer = "CnpcBalanceMaintainer";

    public void reload() {

//        Map<Integer, Long> balances = new HashMap<>();
//
//        List<InquireBalanceTask> tasks = new ArrayList<>();
//        for (CnpcProvidable providable : dispatcher.getCnpcProvidables()) {
//            tasks.add(new InquireBalanceTask(providable));
//        }
//        try {
//            executorService.invokeAll(tasks).stream().forEach((f) -> {
//                if (f.get(5, TimeUnit.SECONDS).longValue() >= 0) {
//
//                }
//            });
//        } catch (InterruptedException ex) {
//
//        }
    }

    class InquireBalanceTask implements Callable<Long> {

        private CnpcProvidable cnpcProvidable;


        public InquireBalanceTask(CnpcProvidable cnpcProvidable) {
            this.cnpcProvidable = cnpcProvidable;
        }

        @Override
        public Long call() throws Exception {
            return cnpcProvidable.balance();
        }
    }
}
