package com.skhu.luxuryshop.cart.exception;

public class NotExistCartException extends IllegalArgumentException {
    public NotExistCartException(String message) {
        super(message);
    }
}
