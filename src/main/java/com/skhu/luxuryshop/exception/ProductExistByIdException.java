package com.skhu.luxuryshop.exception;

public class ProductExistByIdException extends IllegalArgumentException {
    public ProductExistByIdException(String message) {
        super(message);
    }
}
