package com.iconiq.demo.service.impl;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import com.iconiq.demo.model.entity.Stock;
import com.iconiq.demo.model.exception.StockNotFoundException;
import com.iconiq.demo.persistance.StockRepository;
import com.iconiq.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularStockServiceImpl implements StockService {

	@Autowired
	private StockRepository repository;

	@Override
	public List<Stock> getAll() {
		return repository.findAll();
	}

	@Override
	public Stock getById(Long id) {
		return repository.getById(id).orElseThrow(() -> new StockNotFoundException("Stock with following id doesn't exist:" + id));
	}

	@Override
	public void update(Stock updatedStock) {
		if(!repository.exists(updatedStock.getId())) {
			throw new StockNotFoundException("Stock with following id doesn't exist:" + updatedStock.getId());
		}

		Stock persistedStock = repository.getOne(updatedStock.getId());
		persistedStock.setCurrentPrice(updatedStock.getCurrentPrice());
		persistedStock.setName(updatedStock.getName());
		persistedStock.setLastUpdated(Date.from(Instant.now()));

		repository.save(persistedStock);
	}

	@Override
	public void create(Stock stock) {
		repository.save(stock);
	}
}
