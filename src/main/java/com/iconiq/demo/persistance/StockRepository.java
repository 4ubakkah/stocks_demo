package com.iconiq.demo.persistance;

import java.util.Optional;

import com.iconiq.demo.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> getById(Long id);
}
