package com.dkds.currency_converter.service.impl;

import com.dkds.currency_converter.dto.ConversionRateResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Slf4j
@Service
public class ExchangeRateService {

    private final String path;
    private final String key;
    private final ObjectMapper objectMapper;

    @Autowired
    public ExchangeRateService(
            @Value("${app.exchangerate-api.path}") String path,
            @Value("${app.exchangerate-api.key}") String key) {
        this.path = path;
        this.key = key;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public HashMap<String, BigDecimal> getCurrencyRates(String currencyCode) {
        log.info("Getting currency rates for {} from external API", currencyCode);
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(path + currencyCode, key)))
                    .GET()
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ConversionRateResponse conversionRateResponse = objectMapper.readValue(response.body(), ConversionRateResponse.class);
            HashMap<String, BigDecimal> map = new HashMap<>(conversionRateResponse.conversionRates().size());
            conversionRateResponse.conversionRates().forEach(
                    (code, value) -> map.put(code, BigDecimal.valueOf(value)));
            return map;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
