package com.iconiq.demo.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.iconiq.demo.model.dto.CreateStockDto;

public class CreateStockDtoFixture {

	public static CreateStockDto regular() {
		return CreateStockDto.builder().name("withoutId")
				.currentPrice(BigDecimal.ONE)
				.lastUpdated(LocalDateTime.now())
				.build();
	}
}