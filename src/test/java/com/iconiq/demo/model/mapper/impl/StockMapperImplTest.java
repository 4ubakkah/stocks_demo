package com.iconiq.demo.model.mapper.impl;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.iconiq.demo.model.dto.CreateStockDto;
import com.iconiq.demo.model.dto.StockDto;
import com.iconiq.demo.model.entity.CreateStockDtoFixture;
import com.iconiq.demo.model.entity.Stock;
import com.iconiq.demo.model.entity.StockDtoFixture;
import com.iconiq.demo.model.entity.StockFixture;
import com.iconiq.demo.model.mapper.StockMapper;
import org.junit.Test;

public class StockMapperImplTest {

	private StockMapper mapper = new StockMapperImpl();

	@Test
	public void shouldMapAllEntityFieldsToDto_whenToDto() throws Exception {
		Stock entity = StockFixture.stockWithId(1L);

		StockDto dto = mapper.toDto(entity);

		assertThat(dto).isEqualToIgnoringGivenFields(entity, "lastUpdated");
		assertThat(dto.getLastUpdated()).isEqualTo(LocalDateTime.ofInstant(entity.getLastUpdated().toInstant(), ZoneId.systemDefault()));
	}

	@Test
	public void shouldMapAllEntitiesToDtos_whenToDtoList() throws Exception {
		List<Stock> stockEntities = Arrays.asList(StockFixture.stockWithId(1L), StockFixture.stockWithId(2L));

		List<StockDto> dtos = mapper.toDtoList(stockEntities);

		assertThat(dtos).hasSize(2);
	}

	@Test
	public void shouldMapAllDtoFieldsToEntity_whenToEntity() throws Exception {
		StockDto dto = StockDtoFixture.dtoWithId(1L);

		Stock entity = mapper.toEntity(dto);

		assertThat(entity).isEqualToIgnoringGivenFields(dto, "lastUpdated", "version");
		assertThat(entity.getLastUpdated()).isEqualTo(Date.from(dto.getLastUpdated().toInstant(ZoneOffset.UTC)));
	}

	@Test
	public void shouldMapAllDtoFieldsToEntity_whenToEntity_givenCreateStockDto() throws Exception {
		CreateStockDto dto = CreateStockDtoFixture.regular();

		Stock entity = mapper.toEntity(dto);

		assertThat(entity).isEqualToIgnoringGivenFields(dto, "lastUpdated", "id", "version");
		assertThat(entity.getLastUpdated()).isEqualTo(Date.from(dto.getLastUpdated().toInstant(ZoneOffset.UTC)));
	}

}