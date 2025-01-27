package com.dkds.currency_converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ConversionRateResponse(@JsonProperty("conversion_rates") Map<String, Double> conversionRates) {
}
