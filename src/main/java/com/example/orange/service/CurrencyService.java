package com.example.orange.service;

import com.example.orange.model.Currency;
import com.example.orange.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Value("${orange.currency.national}")
    private String nationalCurrencyName;

    @PostConstruct
    void init() {
        if (currencyRepository.findByName(nationalCurrencyName).isEmpty()) {
            Currency nationalCurrency = new Currency();
            nationalCurrency.setName(nationalCurrencyName);
            currencyRepository.save(nationalCurrency);
        }

    }
}
