package com.skhu.luxuryshop.user.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(){
        super("중복된 이메일입니다.");
    }
}