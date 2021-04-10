package com.skhu.luxuryshop.user.exception;

public class SignupPasswordUnmatchedException extends IllegalArgumentException {
    public SignupPasswordUnmatchedException(String message) {
        super(message);
    }
}
