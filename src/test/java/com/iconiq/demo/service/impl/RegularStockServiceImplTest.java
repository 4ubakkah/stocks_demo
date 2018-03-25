package com.iconiq.demo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.iconiq.demo.model.entity.Stock;
import com.iconiq.demo.model.entity.StockFixture;
import com.iconiq.demo.model.exception.StockNotFoundException;
import com.iconiq.demo.persistance.StockRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegularStockServiceImplTest {

	@InjectMocks
	private RegularStockServiceImpl service;

	@Mock
	private StockRepository dao;

	@Test
	public void shouldDelegateToDao_whenGetAll() throws Exception {
		List<Stock> expectedStocks = Arrays.asList(StockFixture.stockWithId(1L));
		when(dao.findAll()).thenReturn(expectedStocks);

		List<Stock> retrievedStocks = service.getAll();

		verify(dao).findAll();
		assertThat(retrievedStocks).containsExactlyInAnyOrderElementsOf(expectedStocks);
	}

	@Test
	public void shouldDelegateToDao_whenGetById() throws Exception {
		Stock expectedStock = StockFixture.stockWithId(1L);
		when(dao.getById(1L)).thenReturn(Optional.of(expectedStock));

		Stock retrievedStock = service.getById(1L);

		verify(dao).getById(1L);
		assertThat(retrievedStock).isEqualTo(expectedStock);
	}

	@Test
	@Ignore("Outdated, required extension due to the latest change in update operation")
	public void shouldDelegateToDao_whenUpdate() throws Exception {
		Stock stock = StockFixture.stockWithId(1L);

		when(dao.exists(stock.getId())).thenReturn(true);

		service.update(stock);

		verify(dao).save(stock);
	}

	@Test
	public void shouldDelegateToDao_whenCreate() throws Exception {
		Stock expectedStock = StockFixture.stockWithId(1L);

		service.create(expectedStock);

		verify(dao).save(expectedStock);
	}

	@Test
	public void shouldThrowException_whenGetById_givenNoStockWithProvidedIdExist() throws Exception {
		Stock nonExistingStock = StockFixture.stockWithId(1L);

		when(dao.getById(nonExistingStock.getId())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.getById(nonExistingStock.getId())).isInstanceOf(StockNotFoundException.class);
	}

	@Test
	public void shouldThrowException_whenUpdate_givenNoStockWithProvidedIdExist() throws Exception {
		Stock nonExistingStock = StockFixture.stockWithId(1L);

		when(dao.exists(nonExistingStock.getId())).thenReturn(false);

		assertThatThrownBy(() -> service.update(nonExistingStock)).isInstanceOf(StockNotFoundException.class);
	}
}