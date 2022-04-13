package com.example.orange.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyConvertRequest {
    private String currencyName;
    private int officeId;
    private float amountInForeignCurrency;
    private ConvertType convertType;

    public enum ConvertType{
        BUY, SELL
    }
}
