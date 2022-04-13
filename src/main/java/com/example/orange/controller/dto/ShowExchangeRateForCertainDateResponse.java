package com.example.orange.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowExchangeRateForCertainDateResponse {

    private String currencyName;

    private float rate;

    private float exchangeRate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime date;
}
