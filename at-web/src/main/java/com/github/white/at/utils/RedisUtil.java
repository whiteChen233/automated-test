package com.github.white.at.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public final class RedisUtil {

    public final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ValueOperations<String, Object> ops4Value() {
        return redisTemplate.opsForValue();
    }

    public ListOperations<String, Object> ops4List() {
        return redisTemplate.opsForList();
    }

    public SetOperations<String, Object> ops4Set() {
        return redisTemplate.opsForSet();
    }

    public HashOperations<String, Serializable, Object> ops4Hash() {
        return redisTemplate.opsForHash();
    }

    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    public boolean delete(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public Long delete(final Collection<String> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return 0L;
        }
        return redisTemplate.delete(collection);
    }

    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}

