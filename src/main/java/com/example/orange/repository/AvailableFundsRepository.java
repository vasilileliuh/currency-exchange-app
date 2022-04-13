package com.example.orange.repository;

import com.example.orange.model.AvailableFunds;
import com.example.orange.model.AvailableFundsPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableFundsRepository extends JpaRepository<AvailableFunds, AvailableFundsPK> {
}
