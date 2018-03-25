package com.iconiq.demo.service;

import java.util.List;

import com.iconiq.demo.model.entity.Stock;

public interface StockService {

	List<Stock> getAll();

	Stock getById(Long id);

	void update(Stock stock);

	void create(Stock stock);
}
