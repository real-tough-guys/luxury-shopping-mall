package com.skhu.luxuryshop.product.exception;

public class ProductExistByIdException extends IllegalArgumentException {
    public ProductExistByIdException(String message) {
        super(message);
    }
}
