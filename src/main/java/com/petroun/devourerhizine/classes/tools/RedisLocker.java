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

package com.petroun.devourerhizine.classes.tools;


import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;

@SuppressWarnings("unused")
public class RedisLocker {

    private StringRedisTemplate stringRedisTemplate;

    private String key;

    private String value;

    private Integer expire;

    public RedisLocker(StringRedisTemplate stringRedisTemplate, String key, Integer expire) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.key = key;
        this.expire = expire;
        this.value = UUID.randomUUID().toString();
    }

    public RedisLocker(StringRedisTemplate stringRedisTemplate, String key, String value, Integer expire) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.key = "RDLK_" + key;
        this.value = value;
        this.expire = expire;
    }

    public boolean trylock() {
        return RedisLock.trylock(stringRedisTemplate, key, value, expire);
    }

    public boolean unlock() {
        return RedisLock.unlock(stringRedisTemplate, key, value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Integer getExpire() {
        return expire;
    }
}
