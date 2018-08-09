package com.lijun.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class BaseRedis<K, V> {
    private Logger logger = LoggerFactory.getLogger(BaseRedis.class);
    protected static final Integer LOAD_PAGE_SIZE = 1000;
    @Autowired
    protected RedisTemplate<K, V> redisTemplate;
    @Autowired
    protected RedisTemplate<K, V> transRedisTemplate;

    public BaseRedis() {
    }
}
