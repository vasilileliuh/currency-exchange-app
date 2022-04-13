package com.example.orange.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class AvailableFunds {
    @EmbeddedId
    private AvailableFundsPK availableFundsPK= new AvailableFundsPK();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("officeId")
    private ExchangeOffice office;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("currencyId")
    private Currency currency;

    private Float amount;
}
