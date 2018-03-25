package com.iconiq.demo.model.mapper.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.iconiq.demo.model.dto.CreateStockDto;
import com.iconiq.demo.model.dto.StockDto;
import com.iconiq.demo.model.entity.Stock;
import com.iconiq.demo.model.mapper.StockMapper;
import org.springframework.stereotype.Service;

@Service
public class StockMapperImpl implements StockMapper {
	@Override
	public StockDto toDto(Stock stock) {
		return StockDto.builder()
				.id(stock.getId())
				.name(stock.getName())
				.currentPrice(stock.getCurrentPrice())
				.lastUpdated(LocalDateTime.ofInstant(stock.getLastUpdated().toInstant(), ZoneId.systemDefault()))
				.build();
	}

	@Override
	public List<StockDto> toDtoList(List<Stock> stocks) {
		return stocks.stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public Stock toEntity(StockDto dto) {
		return Stock.builder()
				.id(dto.getId())
				.name(dto.getName())
				.currentPrice(dto.getCurrentPrice())
				.lastUpdated(Date.from(dto.getLastUpdated().toInstant(ZoneOffset.UTC)))
				.build();
	}

	@Override
	public Stock toEntity(CreateStockDto dto) {
		return Stock.builder()
				.name(dto.getName())
				.currentPrice(dto.getCurrentPrice())
				.lastUpdated(Date.from(dto.getLastUpdated().toInstant(ZoneOffset.UTC)))
				.build();
	}
}
