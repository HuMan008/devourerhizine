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

package com.petroun.devourerhizine.service.impl;

import com.petroun.devourerhizine.model.entity.Option;
import com.petroun.devourerhizine.model.entity.OptionExample;
import com.petroun.devourerhizine.model.mapper.OptionMapper;
import com.petroun.devourerhizine.service.OptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionMapper optionMapper;

    private static ConcurrentHashMap<String, String> cache = new ConcurrentHashMap();

    private static Logger logger = LoggerFactory.getLogger(OptionServiceImpl.class);

    @Override
    public String get(String key) {
        return get(key, null);
    }

    @Override
    public String get(String key, String defaultValue) {
        return cache.getOrDefault(key, defaultValue);
    }

    @Override
    public void set(String key, String value) {
        cache.put(key, value);
        Option option = new Option();
        option.setName(key);
        option.setValue(value);
        optionMapper.updateByPrimaryKey(option);
    }

    @Override
    public void reload() {
        List<Option> options = optionMapper.selectByExample(new OptionExample());
        Set<String> keys = new HashSet<>();

        for (String key : cache.keySet()) {
            keys.add(key);
        }

        for (Option option : options) {
            keys.remove(option.getName());
            cache.put(option.getName(), option.getValue());
        }

        for (String key : keys) {
            cache.remove(key);
        }

        logger.info("Reload options,loaded:{} removed:{}", cache, keys);
    }
}
