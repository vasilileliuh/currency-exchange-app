package com.example.orange.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class CurrencyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float exchangeRate;

    private Float rateForeignCurrency;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private Currency foreignCurrency;
}
