package com.example.orange.service;


import com.example.orange.controller.dto.CurrencyConvertRequest;
import com.example.orange.controller.dto.CurrencyExchangeRequest;
import com.example.orange.controller.dto.ShowExchangeRateForCertainDateResponse;
import com.example.orange.model.*;
import com.example.orange.repository.AvailableFundsRepository;
import com.example.orange.repository.CurrencyExchangeRepository;
import com.example.orange.repository.CurrencyRepository;
import com.example.orange.repository.ExchangeOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeOfficeService {

    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final CurrencyRepository currencyRepository;
    private final ExchangeOfficeRepository officeRepository;
    private final AvailableFundsRepository availableFundsRepository;

    @Value("${orange.currency.national}")
    private String nationalCurrencyName;

    public CurrencyExchange registrationTodayRate(CurrencyExchangeRequest currencyExchangeRequest) {
        Currency currency = currencyRepository.findByName(currencyExchangeRequest.getCurrencyName())
                .orElseGet(() -> {
                    Currency newCurrency = new Currency();
                    newCurrency.setName(currencyExchangeRequest.getCurrencyName());
                    return currencyRepository.save(newCurrency);
                });

        currencyExchangeRepository.findByUpdatedAtAndForeignCurrencyName(LocalDateTime.now()
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0), currencyExchangeRequest.getCurrencyName())
                .ifPresent(currencyExchange -> {
                    throw new RuntimeException("Today's exchange rate is already set");
                });

        CurrencyExchange newCurrencyExchange = new CurrencyExchange();
        newCurrencyExchange.setForeignCurrency(currency);
        newCurrencyExchange.setRateForeignCurrency(currencyExchangeRequest.getRate());
        newCurrencyExchange.setExchangeRate(currencyExchangeRequest.getExchangeRate());
        newCurrencyExchange.setUpdatedAt(LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0));
        return currencyExchangeRepository.save(newCurrencyExchange);
    }

    public ShowExchangeRateForCertainDateResponse getCurrencyRateByDate(String currencyName, LocalDateTime date) {
        Currency currency = currencyRepository.findByName(currencyName)
                .orElseThrow(() -> new RuntimeException("Currency is absent"));

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByUpdatedAtAndForeignCurrencyName(date
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0), currency.getName())
                .orElseThrow(() -> new RuntimeException("There is no such data in registry"));

        return ShowExchangeRateForCertainDateResponse.builder()
                .currencyName(currencyExchange.getForeignCurrency().getName())
                .rate(currencyExchange.getRateForeignCurrency())
                .date(currencyExchange.getUpdatedAt())
                .exchangeRate(currencyExchange.getExchangeRate())
                .build();
    }

    public CurrencyConvertRequest currencyConvertTransactionRequest(CurrencyConvertRequest currencyTransaction) {
        Optional<Currency> currency = currencyRepository.findByName(currencyTransaction.getCurrencyName());
        Optional<ExchangeOffice> office = this.officeRepository.findById(currencyTransaction.getOfficeId());
        Optional<Currency> nationalCurrency = currencyRepository.findByName(nationalCurrencyName);

        if (currency.isEmpty() || nationalCurrency.isEmpty()) {
            throw new RuntimeException("Unknown currency");
        }
        if (office.isEmpty()) {
            throw new RuntimeException("Unknown office");
        }

        Optional<CurrencyExchange> todayCurrencyExchangeRate = currencyExchangeRepository.findByUpdatedAtAndForeignCurrencyName(LocalDateTime.now()
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0),
                currency.get().getName());

        if (todayCurrencyExchangeRate.isEmpty()) {
            throw new RuntimeException("There is no such rate in given date");
        }

        float neededAmountNationalMoney = currencyTransaction.getAmountInForeignCurrency()
                / todayCurrencyExchangeRate.get().getRateForeignCurrency()
                * todayCurrencyExchangeRate.get().getExchangeRate();

        if (currencyTransaction.getConvertType() == CurrencyConvertRequest.ConvertType.SELL) {
            Optional<AvailableFunds> availableFunds = availableFundsRepository
                    .findById(new AvailableFundsPK(office.get().getId(), currency.get().getId()));
            if (availableFunds.isEmpty()) {
                throw new RuntimeException("Unknown funds");
            }
            float amountAvailableCurrency = availableFunds.get().getAmount();
            if (amountAvailableCurrency >= currencyTransaction.getAmountInForeignCurrency()) {
                office.get()
                        .getAvailableFundsList().stream()
                        .filter(availableFundsInForeignCurrency -> availableFundsInForeignCurrency.getCurrency().getId()
                                .equals(currency.get().getId()))
                        .findFirst()
                        .ifPresent(foreignAvailableFunds -> {
                            foreignAvailableFunds.setAmount(foreignAvailableFunds.getAmount()
                                    - currencyTransaction.getAmountInForeignCurrency());

                            availableFundsRepository.save(foreignAvailableFunds);
                        });

                AvailableFunds nationalCurrencyAvailableFunds = office.get()
                        .getAvailableFundsList().stream()
                        .filter(availableFundsInNationalCurrency -> availableFundsInNationalCurrency.getCurrency().getId()
                                .equals(nationalCurrency.get().getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Something went wrong"));

                nationalCurrencyAvailableFunds.setAmount(nationalCurrencyAvailableFunds.getAmount() + neededAmountNationalMoney);
                availableFundsRepository.save(nationalCurrencyAvailableFunds);
            } else {
                throw new RuntimeException("Not enough funds in foreign currency");
            }
        } else {
            Optional<AvailableFunds> availableNationalFunds = availableFundsRepository
                    .findById(new AvailableFundsPK(office.get().getId(), nationalCurrency.get().getId()));
            if (availableNationalFunds.isEmpty()) {
                throw new RuntimeException("Unknown funds");
            }
            if (availableNationalFunds.get().getAmount() >= neededAmountNationalMoney) {
                office.get()
                        .getAvailableFundsList().stream()
                        .filter(availableFunds -> availableFunds.getCurrency().getId().equals(nationalCurrency.get().getId()))
                        .findFirst()
                        .ifPresent(nationalAvailableFunds -> {
                            nationalAvailableFunds.setAmount(nationalAvailableFunds.getAmount() - neededAmountNationalMoney);
                            availableFundsRepository.save(nationalAvailableFunds);
                        });

                AvailableFunds currencyAvailableFunds = office.get()
                        .getAvailableFundsList().stream()
                        .filter(availableFunds -> availableFunds.getCurrency().getId().equals(currency.get().getId()))
                        .findFirst()
                        .orElseGet(() -> {
                            AvailableFundsPK availableFundsPK = new AvailableFundsPK(office.get().getId(), currency.get().getId());
                            AvailableFunds availableFunds = new AvailableFunds();
                            availableFunds.setAvailableFundsPK(availableFundsPK);
                            availableFunds.setOffice(office.get());
                            availableFunds.setCurrency(currency.get());
                            availableFunds.setAmount(0f);
                            return availableFundsRepository.save(availableFunds);
                        });
                currencyAvailableFunds.setAmount(currencyAvailableFunds.getAmount() + currencyTransaction.getAmountInForeignCurrency());
                availableFundsRepository.save(currencyAvailableFunds);
            } else {
                throw new RuntimeException("Not enough funds in local currency");
            }
        }
        return null;
    }
}
