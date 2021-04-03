package com.skhu.luxuryshop.user.controllerAdvice;

import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.FailedToSignupException;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("유효성 검증 실패", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> duplicatedEmailException(DuplicatedEmailException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("중복된 이메일", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignupPasswordUnmatchedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> signupPasswordUnMatchException(SignupPasswordUnmatchedException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("비밀번호 불일치", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToSignupException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> failedToSignupException(FailedToSignupException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("회원가입 실패", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> exception(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity("기타에러", HttpStatus.NOT_FOUND);
    }
}