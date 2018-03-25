package com.iconiq.demo.model.mapper;

import java.util.List;

import com.iconiq.demo.model.dto.CreateStockDto;
import com.iconiq.demo.model.dto.StockDto;
import com.iconiq.demo.model.entity.Stock;

public interface StockMapper {

	StockDto toDto(Stock stock);

	List<StockDto> toDtoList(List<Stock> stock);

	Stock toEntity(StockDto dto);

	Stock toEntity(CreateStockDto dto);
}
