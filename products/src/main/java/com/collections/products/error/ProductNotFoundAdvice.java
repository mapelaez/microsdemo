package com.collections.products.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
class ProductNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ProductNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorResponse productNotFoundHandler(ProductNotFoundException ex) {
    return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
  }
}