package com.skhu.luxuryshop.user.exception;

public class NoUserFoundException extends IllegalArgumentException {
    public NoUserFoundException() {
        super("해당 유저가 존재하지 않습니다.");
    }
}