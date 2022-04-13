package com.example.orange.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ExchangeOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String officeName;

    @OneToMany(mappedBy = "office", fetch = FetchType.EAGER)
    private List<AvailableFunds> availableFundsList;

}
