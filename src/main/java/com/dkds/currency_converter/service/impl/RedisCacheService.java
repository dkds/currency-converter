package com.dkds.currency_converter.service.impl;

import com.dkds.currency_converter.service.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisCacheService implements CacheService {

    private final JedisPool pool;

    public RedisCacheService(
            @Value("${app.redis.host}") String host,
            @Value("${app.redis.port}") Integer port) {
        pool = new JedisPool(host, port);
    }

    @Override
    public String get(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public void set(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value);
        }
    }
}
