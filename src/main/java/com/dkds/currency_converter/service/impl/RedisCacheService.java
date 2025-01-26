package com.dkds.currency_converter.service.impl;

import com.dkds.currency_converter.service.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Set;

@Service
public class RedisCacheService implements CacheService {

    private final JedisPool pool;
    private final String prefix;

    public RedisCacheService(
            @Value("${app.redis.prefix}") String prefix,
            @Value("${app.redis.host}") String host,
            @Value("${app.redis.port}") Integer port) {
        pool = new JedisPool(host, port);
        this.prefix = prefix;
    }

    @Override
    public String get(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(prefix + ":" + key);
        }
    }

    @Override
    public void set(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(prefix + ":" + key, value);
        }
    }

    @Override
    public void hset(String key, String field, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(prefix + ":" + key, field, value);
        }
    }

    @Override
    public void hsetBulk(String key, HashMap<String, String> hashMap) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(prefix + ":" + key, hashMap);
        }
    }

    @Override
    public String hget(String key, String field) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hget(prefix + ":" + key, field);
        }
    }

    @Override
    public Set<String> keys() {
        try (Jedis jedis = pool.getResource()) {
            return jedis.keys(prefix);
        }
    }
}
