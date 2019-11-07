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

import cn.gotoil.bill.tools.encoder.Hash;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

@SuppressWarnings("unused")
public class RedisLock {

    private static RedisScript<Long> lockScript;

    private static RedisScript<Long> unlockScript;

    static {
        String lockLua = "if (redis.call('setnx', KEYS[1], ARGV[1]) == 1) then " +
                "return redis.call('expire', KEYS[1], tonumber(ARGV[2])) else return 0 end";
        lockScript = new CRedisScript<>(lockLua, Long.class);

        String unlockLua = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                "then return redis.call('del', KEYS[1]) else return 0 end";
        unlockScript = new CRedisScript<>(unlockLua, Long.class);
    }

    /**
     * @param redisTemplate
     * @param key
     * @param value
     * @param expireSec
     * @return
     */
    public static boolean trylock(StringRedisTemplate redisTemplate, String key, String value, int expireSec) {
        Long result = redisTemplate.execute(lockScript, Collections.singletonList(key), value, String.valueOf(expireSec));
        return result.intValue() == 1;
    }

    /**
     * @param redisTemplate
     * @param key
     * @param value
     * @return
     */
    public static boolean unlock(StringRedisTemplate redisTemplate, String key, String value) {
        Long result = redisTemplate.execute(unlockScript, Collections.singletonList(key), value);
        return result.intValue() == 1;
    }

    /**
     * @param <T>
     */
    private static class CRedisScript<T> implements RedisScript<T> {

        private final String script;
        private final String sha1;
        private final Class<T> resultType;

        public CRedisScript(String script, Class<T> resultType) {
            this.script = script;
            this.sha1 = Hash.sha1(script);
            this.resultType = resultType;
        }

        @Override
        public String getSha1() {
            return sha1;
        }

        @Override
        public Class<T> getResultType() {
            return resultType;
        }

        @Override
        public String getScriptAsString() {
            return script;
        }
    }
}
