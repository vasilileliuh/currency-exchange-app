package com.example.orange.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyExchangeRequest {
    private String currencyName;
    private float rate;
    private float exchangeRate;
}
