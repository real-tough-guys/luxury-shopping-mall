package com.skhu.luxuryshop.product.controllerAdvice;

import com.skhu.luxuryshop.product.exception.ProductExistByIdException;
import com.skhu.luxuryshop.product.exception.ProductFindByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.skhu.luxuryshop.product.controller")
@Slf4j
public class ProductExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductFindByIdException.class)
    public ResponseEntity<String> productFindByIdException(ProductFindByIdException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductExistByIdException.class)
    public ResponseEntity<String> productExistByproductNameException(ProductExistByIdException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 에러.");
    }
}