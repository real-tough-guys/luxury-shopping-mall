package com.skhu.luxuryshop.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupPasswordUnmatchedException extends RuntimeException {
    public SignupPasswordUnmatchedException(String message) {
        super(message);
    }
}
