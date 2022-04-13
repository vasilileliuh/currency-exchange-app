package com.example.orange.repository;

import com.example.orange.model.Currency;
import com.example.orange.model.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    Optional<CurrencyExchange> findByUpdatedAtAndForeignCurrencyName(LocalDateTime localDateTime, String foreignCurrencyName);
}
