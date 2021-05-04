package com.skhu.luxuryshop.product.exception;

public class ProductFindByIdException extends IllegalArgumentException {
    public ProductFindByIdException(String message) {
        super(message);
    }
}
