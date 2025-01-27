package com.dkds.currency_converter.service.impl;

import com.dkds.currency_converter.service.CacheService;
import com.dkds.currency_converter.service.ConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

@Slf4j
@Service
public class ExternalConverterService implements ConverterService {

    private final CacheService cacheService;
    private final ExchangeRateService exchangeRateService;
    private Set<String> currencyCodes;

    @Autowired
    public ExternalConverterService(
            CacheService cacheService,
            ExchangeRateService exchangeRateService) {
        this.cacheService = cacheService;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public BigDecimal convert(String currencyCodeFrom, String currencyCodeTo, BigDecimal value) {
        var currencyRateStr = cacheService.hget(currencyCodeFrom, currencyCodeTo);
        BigDecimal currencyRate;
        if (currencyRateStr == null) {
            var currencyRates = exchangeRateService.getCurrencyRates(currencyCodeFrom);
            currencyRate = currencyRates.get(currencyCodeTo);
            HashMap<String, String> map = new HashMap<>(currencyRates.size());
            currencyRates.forEach((key, rate) -> map.put(key, rate.toPlainString()));
            cacheService.hsetBulk(currencyCodeFrom, map);
        } else {
            log.info("Cached rates found for {}", currencyCodeFrom);
            currencyRate = BigDecimal.valueOf(Double.parseDouble(currencyRateStr));
        }
        return currencyRate.multiply(value);
    }

    @Override
    public Set<String> getCurrencyCodes() {
        if (currencyCodes == null) {
            var currencyRates = exchangeRateService.getCurrencyRates("USD");
            currencyCodes = currencyRates.keySet();
        }
        return currencyCodes;
    }
}
