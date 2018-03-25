package com.iconiq.demo.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.iconiq.demo.model.dto.CreateStockDto;
import com.iconiq.demo.model.dto.StockDto;
import com.iconiq.demo.model.entity.CreateStockDtoFixture;
import com.iconiq.demo.model.entity.Stock;
import com.iconiq.demo.model.entity.StockDtoFixture;
import com.iconiq.demo.model.entity.StockFixture;
import com.iconiq.demo.model.mapper.StockMapper;
import com.iconiq.demo.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StocksIconiqAPITest {

	@InjectMocks
	private StocksIconiqAPI api;

	@Mock
	private StockService service;

	@Mock
	private StockMapper mapper;

	@Test
	public void shouldDelegate_whenCreate() throws Exception {
		CreateStockDto dto = CreateStockDtoFixture.regular();
		Stock entity = StockFixture.stockWithoutId();
		when(mapper.toEntity(dto)).thenReturn(entity);

		api.createStock(dto);

		Mockito.verify(service).create(entity);
	}

	@Test
	public void shouldDelegate_whenUpdate() throws Exception {
		StockDto dto = StockDtoFixture.dtoWithId(1L);
		Stock entity = StockFixture.stockWithId(1L);
		when(mapper.toEntity(dto)).thenReturn(entity);

		api.updateStock(dto);

		Mockito.verify(service).update(entity);
	}

	@Test
	public void shouldDelegate_whenGetStock() throws Exception {
		StockDto dto = StockDtoFixture.dtoWithId(1L);
		Stock entity = StockFixture.stockWithId(1L);
		when(service.getById(dto.getId())).thenReturn(entity);
		when(mapper.toDto(entity)).thenReturn(dto);

		StockDto retrievedDto = api.getStock(dto.getId()).getBody();

		Mockito.verify(service).getById(dto.getId());
		assertThat(retrievedDto).isEqualTo(dto);
	}

	@Test
	public void shouldDelegate_whenGetAllStocks() throws Exception {
		List<StockDto> dtos = Arrays.asList(StockDtoFixture.dtoWithId(1L), StockDtoFixture.dtoWithId(2L));
		List<Stock> entities = Arrays.asList(StockFixture.stockWithId(1L), StockFixture.stockWithId(2L));
		when(service.getAll()).thenReturn(entities);
		when(mapper.toDtoList(entities)).thenReturn(dtos);

		List<StockDto> retrievedDtos = api.getAllStocks().getBody();

		Mockito.verify(service).getAll();
		assertThat(retrievedDtos).containsExactlyInAnyOrderElementsOf(dtos);
	}

}