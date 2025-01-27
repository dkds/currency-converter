package com.dkds.currency_converter.service;

import java.math.BigDecimal;
import java.util.Set;

public interface ConverterService {
    BigDecimal convert(String currencyCodeFrom, String currencyCodeTo, BigDecimal value);

    Set<String> getCurrencyCodes();
}
