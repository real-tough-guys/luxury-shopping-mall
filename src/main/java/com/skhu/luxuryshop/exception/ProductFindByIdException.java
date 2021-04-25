package com.skhu.luxuryshop.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductFindByIdException extends IllegalArgumentException {
    public ProductFindByIdException(String message) {
        super(message);
    }
}
