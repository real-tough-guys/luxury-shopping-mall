package com.skhu.luxuryshop.user.controller;

import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.service.UserSignupService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserSignupService userSignupService;

    //이메일 중복검사
    @GetMapping("/email")
    public ResponseEntity<Boolean> isDuplicatedEmail(@RequestBody String Email) {
        boolean checkResult = userSignupService.isDuplicatedEmail(Email);
        return new ResponseEntity(checkResult, HttpStatus.OK);
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid UserSignupDto userSignupDto) {
        userSignupService.save(userSignupDto);
        return new ResponseEntity("회원가입 성공", HttpStatus.OK);
    }
}