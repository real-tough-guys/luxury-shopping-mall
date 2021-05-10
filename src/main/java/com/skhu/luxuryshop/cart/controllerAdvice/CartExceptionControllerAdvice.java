package com.skhu.luxuryshop.cart.controllerAdvice;

import com.skhu.luxuryshop.cart.exception.NotExistCartException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CartExceptionControllerAdvice {
    @ExceptionHandler(NotExistCartException.class)
    public ResponseEntity<String> notExistCartException(NotExistCartException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
