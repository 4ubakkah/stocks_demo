package com.iconiq.demo.model.exception;


public class StockNotFoundException extends RuntimeException {

	public StockNotFoundException(String message) {
		super(message);
	}
}
