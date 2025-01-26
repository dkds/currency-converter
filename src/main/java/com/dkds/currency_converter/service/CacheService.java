package com.dkds.currency_converter.service;

import java.util.HashMap;
import java.util.Set;

public interface CacheService {
    String get(String key);

    void set(String key, String value);

    void hset(String key, String field, String value);

    void hsetBulk(String key, HashMap<String, String> hashMap);

    String hget(String key, String field);

    Set<String> keys();
}
