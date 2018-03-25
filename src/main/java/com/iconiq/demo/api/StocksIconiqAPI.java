package com.iconiq.demo.api;

import java.util.List;

import com.iconiq.demo.model.dto.CreateStockDto;
import com.iconiq.demo.model.dto.StockDto;
import com.iconiq.demo.model.mapper.StockMapper;
import com.iconiq.demo.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/stocks", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api
@Slf4j
public class StocksIconiqAPI {

	@Autowired
	private StockService stockService;

	@Autowired
	private StockMapper stockMapper;

	@ApiOperation(value = "Create a stock", httpMethod = "POST")
	@PostMapping(value = "")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> createStock(@RequestBody CreateStockDto requestDto) {
		log.info("Received request to create next stock: {}", requestDto);

		stockService.create(stockMapper.toEntity(requestDto));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Update the stock", httpMethod = "PUT")
	@PutMapping(value = "1")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> updateStock(@RequestBody StockDto requestDto) {
		log.info("Received request to create next stock: {}", requestDto);

		stockService.update(stockMapper.toEntity(requestDto));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Get the stock with provided id", httpMethod = "GET")
	@GetMapping(value = "1")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Stock with provided id not found") })
	public ResponseEntity<StockDto> getStock(@ApiParam(value = "Requested stock id", required = true) @RequestParam(value = "stockId") Long id) {
		log.info("Received request to get stock with following id: {}", id);

		return new ResponseEntity<>(stockMapper.toDto(stockService.getById(id)), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all stocks", httpMethod = "GET")
	@GetMapping(value = "")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<StockDto>> getAllStocks() {
		log.info("Received request to get all stocks.");

		return new ResponseEntity<>(stockMapper.toDtoList(stockService.getAll()), HttpStatus.OK);
	}
}