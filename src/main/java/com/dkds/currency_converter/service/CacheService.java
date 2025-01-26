package com.dkds.currency_converter.service;

public interface CacheService {
    String get(String key);

    void set(String key, String value);
}
