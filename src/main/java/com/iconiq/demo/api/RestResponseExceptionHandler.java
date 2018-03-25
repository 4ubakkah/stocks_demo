package com.iconiq.demo.api;

import com.iconiq.demo.model.dto.ErrorResponseDto;
import com.iconiq.demo.model.exception.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    protected ResponseEntity<Object> handleStockNotFound(RuntimeException ex, WebRequest request) {
        log.error("Error: {}", ex.getMessage(), ex);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("Requested stock not found.");
        return handleExceptionInternal(ex, errorResponseDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
