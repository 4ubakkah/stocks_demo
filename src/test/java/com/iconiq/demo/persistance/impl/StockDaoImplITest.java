package com.iconiq.demo.persistance.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.iconiq.demo.model.entity.Stock;
import com.iconiq.demo.model.entity.StockFixture;
import com.iconiq.demo.persistance.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockDaoImplITest {

	@Autowired
	private StockRepository dao;

	@Before
	public void setUp() {
		assertThat(dao.findAll()).isEmpty();
	}

	@Test
	public void shouldReturnAllStocks_whenGetAll_givenTwoItemsExist() throws Exception {
		Stock stock1 = StockFixture.stockWithId(1L);
		Stock stock2 = StockFixture.stockWithId(2L);

		dao.save(stock1);
		dao.save(stock2);

		assertThat(dao.findAll()).hasSize(2)
				.containsExactlyInAnyOrder(stock1, stock2);
	}

	@Test
	public void shouldReturnStock_whenGetById_givenStockWithProvidedIdExist() throws Exception {
		Stock stock = StockFixture.stockWithoutId();

		Stock persistedStock = dao.save(stock);

		Optional<Stock> retrieved = dao.getById(persistedStock.getId());

		assertThat(retrieved).isPresent();
		assertThat(retrieved.get()).isEqualToIgnoringGivenFields(stock, "id");
	}

	@Test
	public void shouldReturnEmptyOptional_whenGetById_givenNoStockWithProvidedIdExist() throws Exception {
		Stock stock = StockFixture.stockWithId(1L);

		dao.save(stock);

		Optional<Stock> retrieved = dao.getById(2L);

		assertThat(retrieved).isNotPresent();
	}

	@Test
	public void shouldUpdateExistingStock_whenUpdate_givenStockWithProvidedIdExist() throws Exception {
		Stock initialStock = Stock.builder().id(1L)
				.name("initial_name")
				.version(0)
				.currentPrice(BigDecimal.ONE)
				.lastUpdated(Date.from(Instant.now()))
				.build();

		Stock persistedStock = dao.save(initialStock);
		assertThat(persistedStock).isEqualToIgnoringGivenFields(initialStock, "id");

		Stock updatedStock = Stock.builder().id(persistedStock.getId())
				.name("very_different_name")
				.version(0)
				.currentPrice(BigDecimal.ZERO)
				.lastUpdated(Date.from(Instant.now()))
				.build();

		dao.save(updatedStock);

		assertThat(dao.getById(persistedStock.getId()).get())
				.isEqualToIgnoringGivenFields(updatedStock, "version");
	}

	@Test
	public void shouldCreateStock_whenCreate() throws Exception {
		Stock stock = StockFixture.stockWithoutId();

		dao.saveAndFlush(stock);

		List<Stock> retrievedStocks = dao.findAll();
		assertThat(retrievedStocks).hasSize(1);
		assertThat(retrievedStocks.get(0)).isEqualToIgnoringGivenFields(stock, "id");
	}
}