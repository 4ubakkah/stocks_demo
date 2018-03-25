package com.iconiq.demo.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.iconiq.demo.model.dto.StockDto;

public class StockDtoFixture {

	public static StockDto dtoWithoutId() {
		return StockDto.builder().name("withoutId")
				.currentPrice(BigDecimal.ONE)
				.lastUpdated(LocalDateTime.now())
				.build();
	}

	public static StockDto dtoWithId(Long id) {
		return StockDto.builder().name("withId")
				.id(id)
				.currentPrice(BigDecimal.ONE)
				.lastUpdated(LocalDateTime.now())
				.build();
	}
}