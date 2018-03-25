package com.iconiq.demo.model.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;

public class StockFixture {

	public static Stock stockWithoutId() {
		return Stock.builder().name("withoutId")
				.currentPrice(BigDecimal.valueOf(1, 2))
				.version(0)
				.lastUpdated(Date.from(Instant.now()))
				.build();
	}

	public static Stock stockWithId(Long id) {
		return Stock.builder().name("withId")
				.id(id)
				.version(0)
				.currentPrice(BigDecimal.valueOf(1, 2))
				.lastUpdated(Date.from(Instant.now()))
				.build();
	}
}