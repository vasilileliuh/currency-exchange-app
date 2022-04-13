package com.example.orange;

import com.example.orange.model.AvailableFunds;
import com.example.orange.model.Currency;
import com.example.orange.model.CurrencyExchange;
import com.example.orange.model.ExchangeOffice;
import com.example.orange.repository.AvailableFundsRepository;
import com.example.orange.repository.CurrencyExchangeRepository;
import com.example.orange.repository.CurrencyRepository;
import com.example.orange.repository.ExchangeOfficeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class JpaCompositeTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private AvailableFundsRepository availableFundsRepository;

    @Autowired
    private ExchangeOfficeRepository exchangeOfficeRepository;

    @Test
    @Disabled
    void test1() {
        saveStubEnviroment();

        List<ExchangeOffice> offices = exchangeOfficeRepository.findAll();
        Assertions.assertEquals(1, offices.size());

        ExchangeOffice exchangeOffice = offices.get(0);

        Assertions.assertEquals("Test office", exchangeOffice.getOfficeName());


        List<AvailableFunds> availableFundsList = exchangeOffice.getAvailableFundsList();
        Assertions.assertEquals(1, availableFundsList.size());

        AvailableFunds availableFunds = availableFundsList.get(0);
        Assertions.assertEquals(100_000_000, availableFunds.getAmount());
        Assertions.assertEquals("LEI", availableFunds.getCurrency().getName());


    }

    private void saveStubEnviroment() {

        Currency currencyLei = new Currency();
        currencyLei.setName("LEI");
        currencyLei = currencyRepository.save(currencyLei);

        Currency currencyEur = new Currency();
        currencyEur.setName("EUR");
        currencyEur = currencyRepository.save(currencyEur);

        ExchangeOffice exchangeOffice = new ExchangeOffice();
        exchangeOffice.setOfficeName("Test office");
        exchangeOffice = exchangeOfficeRepository.save(exchangeOffice);

        CurrencyExchange currencyExchange = new CurrencyExchange();
        currencyExchange.setExchangeRate(21f);
        currencyExchange.setRateForeignCurrency(1f);
        currencyExchange.setForeignCurrency(currencyEur);
        currencyExchange.setUpdatedAt(LocalDateTime.now().withNano(0));
        currencyExchange = currencyExchangeRepository.save(currencyExchange);

        AvailableFunds availableFunds = new AvailableFunds();
        availableFunds.setOffice(exchangeOffice);
        availableFunds.setCurrency(currencyLei);
        availableFunds.setAmount(100_000_000f);
        availableFunds = availableFundsRepository.save(availableFunds);
    }
}

