package com.dkds.currency_converter.controller;

import com.dkds.currency_converter.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final ConverterService counterService;

    @Autowired
    public CurrencyController(ConverterService converterService) {
        this.counterService = converterService;
    }

    @GetMapping("currency-codes")
    public ResponseEntity<Set<String>> listCurrencyCodes() {
        return ResponseEntity.ok(counterService.getCurrencyCodes());
    }

    @GetMapping("convert/{fromCurrency}/{toCurrency}/{value}")
    public ResponseEntity<BigDecimal> convert(
            @PathVariable("fromCurrency") String fromCurrency,
            @PathVariable("toCurrency") String toCurrency,
            @PathVariable("value") String value) {
        var converted = counterService.convert(
                fromCurrency, toCurrency, BigDecimal.valueOf(Double.parseDouble(value))
        );
        return ResponseEntity.ok(converted);
    }
}
