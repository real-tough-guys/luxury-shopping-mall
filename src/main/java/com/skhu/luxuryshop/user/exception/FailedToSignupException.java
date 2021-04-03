package com.skhu.luxuryshop.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FailedToSignupException extends RuntimeException {
    public FailedToSignupException(String message) {
        super(message);
    }
}