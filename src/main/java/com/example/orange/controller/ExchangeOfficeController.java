package com.example.orange.controller;

import com.example.orange.controller.dto.CurrencyConvertRequest;
import com.example.orange.controller.dto.CurrencyExchangeRequest;
import com.example.orange.service.ExchangeOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("/currency")
public class ExchangeOfficeController {

    @Autowired
    private ExchangeOfficeService exchangeOfficeService;

    @PostMapping("/registrationTodayRate")
    public ResponseEntity registrationTodayRate(@RequestBody CurrencyExchangeRequest currencyExchangeRequest) {
        try {
            exchangeOfficeService.registrationTodayRate(currencyExchangeRequest);
            return ResponseEntity.ok("Currency rate is successfully saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getCurrencyRateByDate")
    public ResponseEntity getCurrencyRateByDate(@RequestParam String currencyName,
                                                @RequestParam("date") String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(dateString, dateTimeFormatter);
        LocalDateTime date = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());

        try {
            return ResponseEntity.ok(exchangeOfficeService.getCurrencyRateByDate(currencyName.toUpperCase(Locale.ROOT), date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong :-(");
        }
    }

    @PostMapping("/currencyConvertRequest")
    public ResponseEntity currencyConvertTransactionRequest(@RequestBody CurrencyConvertRequest currencyTransaction) {
        try {
            exchangeOfficeService.currencyConvertTransactionRequest(currencyTransaction);
            return ResponseEntity.ok("Transaction is successfully saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
