package com.skhu.luxuryshop.user.controllerAdvice;

import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> duplicatedEmailException(DuplicatedEmailException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("중복된 이메일입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignupPasswordUnmatchedException.class)
    public ResponseEntity<String> signupPasswordUnMatchException(SignupPasswordUnmatchedException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
    }
}