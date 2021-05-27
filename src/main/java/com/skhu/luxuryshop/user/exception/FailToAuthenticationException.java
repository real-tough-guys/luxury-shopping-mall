package com.skhu.luxuryshop.user.exception;

public class FailToAuthenticationException extends RuntimeException {
    public FailToAuthenticationException() {
        super("접근 권한이 없습니다.");
    }
}