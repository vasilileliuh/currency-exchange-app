package com.example.orange.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AvailableFundsPK implements Serializable {
    @Column(name = "office_id")
    Integer officeId;

    @Column(name = "currency_id")
    Integer currencyId;

}
