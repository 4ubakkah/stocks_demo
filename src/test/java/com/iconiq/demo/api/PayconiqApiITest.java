package com.iconiq.demo.api;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;
import java.util.List;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.iconiq.demo.DemoApplication;
import com.iconiq.demo.model.dto.ErrorResponseDto;
import com.iconiq.demo.model.dto.StockDto;
import com.iconiq.demo.model.entity.Stock;
import com.iconiq.demo.model.entity.StockFixture;
import com.iconiq.demo.persistance.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
@Transactional
public class PayconiqApiITest {

	private static final String GET_ALL_STOCKS_PATH = "/api/stocks";
	private static final String GET_STOCK_BY_ID_PATH = "/api/stocks/1";

	@Autowired
	private WebApplicationContext webAppContext;

	private MockMvc mvc;

	@Autowired
	private StockRepository dao;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webAppContext)
				.build();

		objectMapper.registerModules(new Jdk8Module());
		objectMapper.registerModules(new JavaTimeModule());
	}

	@Test
	public void shouldReturnOk_whenGetAll_givenNoStocks() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_STOCKS_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<StockDto> dto = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<StockDto>>() {});

		assertThat(dto).isEmpty();
	}

	@Test
	public void shouldReturnOk_whenGetAll_givenExistingStocks() throws Exception {
		Stock stock = StockFixture.stockWithoutId();
		dao.save(stock);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_ALL_STOCKS_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<StockDto> dto = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<StockDto>>() {});
		assertThat(dto).isNotEmpty().hasSize(1);
		assertThat(dto.get(0)).isEqualToIgnoringGivenFields(stock, "id", "lastUpdated");
	}

	@Test
	public void shouldReturnOk_whenGetById_givenNoStockExist() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_STOCK_BY_ID_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.param("stockId", "777")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		ErrorResponseDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponseDto.class);

		assertThat(dto.getDescription()).isNotEmpty();
	}

	@Test
	public void shouldReturnOk_whenGetById_givenExistingStock() throws Exception {
		Stock stock = StockFixture.stockWithoutId();
		Stock persistedStock = dao.save(stock);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(GET_STOCK_BY_ID_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.param("stockId", persistedStock.getId().toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		StockDto dto = objectMapper.readValue(result.getResponse().getContentAsString(), StockDto.class);
		assertThat(dto).isNotNull();
		assertThat(dto).isEqualToIgnoringGivenFields(stock, "id", "lastUpdated");
	}
}