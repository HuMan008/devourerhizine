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
import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.service.OptionService;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Dispatcher {

    @Autowired
    private List<CnpcProvidable> cnpcProvidables;

    @Autowired
    private OptionService optionService;

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    private String cnpcProviderPrioritiesOptionValue = "";
    private Map<String, Integer> cnpcProviderPriorities = new HashMap<>();


    public List<CnpcProvidable> getCnpcProvidables() {
        return cnpcProvidables;
    }


    /**
     * @param providerId
     * @return
     */
    public CnpcProvidable provider(Integer providerId) {
        if (cnpcProvidables.size() < 1) {
            throw new BillException(CnpcProviderError.UnableFindSupportedAmountProvider);
        }
        for (CnpcProvidable providable : cnpcProvidables) {
            if (providable.id().equals(providerId)) {
                return providable;
            }
        }

        return null;
    }

    /**
     * @param amount
     * @param director
     * @return
     */
    public CnpcProvidable provider(Integer amount, Director director) {

        if (cnpcProvidables.size() < 1) {
            throw new BillException(CnpcProviderError.UnableFindSupportedAmountProvider);
        }

        Map<String, CnpcProvidable> supported = new HashMap();
        for (CnpcProvidable providable : cnpcProvidables) {
            if (!providable.supportedDirector(director)) {
                continue;
            }

            if (providable.supportedAmount(amount, director)) {
                supported.put(providable.name(), providable);
            }
        }

        CnpcProvidable provider = pick(supported);
        if (provider == null) {
            throw new BillException(CnpcProviderError.UnableFindSupportedAmountProvider);
        }
        return provider;
    }

    /**
     * @param supported
     * @return
     */
    private CnpcProvidable pick(Map<String, CnpcProvidable> supported) {
        if (supported.size() < 1) {
            return null;
        }

        if (supported.size() == 1) {
            return supported.entrySet().iterator().next().getValue();
        }

        Map<String, Integer> priorities = providerPriorities();
        RangePicker rangePicker = new RangePicker();
        for (String key : supported.keySet()) {
            int pri = priorities.getOrDefault(key, 0);
            if (pri > 0) {
                rangePicker.add(key, pri);
            }
        }
        RangePicker.Pair pair = rangePicker.random();
        if (pair == null) {
            return null;
        }

        return supported.get(pair.getName());
    }

    /**
     * @return
     */
    private Map<String, Integer> providerPriorities() {
        String optionValue = optionService.get(OptionKeys.CnpcProviderPriorities, "");
        if (cnpcProviderPrioritiesOptionValue.equals(optionValue)) {
            return cnpcProviderPriorities;
        }

        synchronized (cnpcProviderPriorities) {
            if (!cnpcProviderPrioritiesOptionValue.equals(optionValue)) {
                cnpcProviderPrioritiesOptionValue = optionValue;
                cnpcProviderPriorities.clear();
                try {
                    String[] splits = cnpcProviderPrioritiesOptionValue.split(",");
                    for (int i = 0; i < splits.length; i++) {
                        String[] item = splits[i].trim().split(":");
                        if (item.length == 2) {
                            Integer value = Integer.parseInt(item[1]);
                            cnpcProviderPriorities.put(item[0], value);
                        } else {
                            logger.error("Unable parse value:{}", splits[i]);
                        }
                    }
                } catch (Exception ex) {
                    logger.error("{}", ex);
                }
            }
        }

        return cnpcProviderPriorities;
    }

    private class RangePicker {

        private Integer maxValue = 0;
        private List<Pair> pairList = new ArrayList<>();

        public void add(String name, Integer value) {
            int start = maxValue.intValue();
            maxValue += value;
            int end = maxValue.intValue();
            pairList.add(new Pair(start, end, name));
        }

        public Pair get(Integer value) {
            if (value < 0 || value > maxValue) {
                return null;
            }

            for (Pair pair : pairList) {
                if (pair.end >= value && pair.start <= value) {
                    return pair;
                }
            }

            return null;
        }

        public Pair random() {
            int rand = RandomUtils.nextInt(0, maxValue);
            return get(rand);
        }

        class Pair {
            int start;
            int end;
            String name;

            public Pair(int start, int end, String name) {
                this.start = start;
                this.end = end;
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }
    }
}
