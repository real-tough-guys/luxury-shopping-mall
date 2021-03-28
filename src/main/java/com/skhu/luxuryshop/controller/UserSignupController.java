package com.skhu.luxuryshop.controller;

import com.skhu.luxuryshop.dto.UserSignupDto;
import com.skhu.luxuryshop.service.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
public class UserSignupController {
    @Autowired
    UserSignupService userSignupService;

    //이메일 중복검사, 중복되지 않는 경우 true
    @GetMapping("")
    public boolean checkEmail(@RequestBody String Email) {
        return userSignupService.verifyEmail(Email);
    }

    //회원가입
    @PostMapping("")
    public void userSignup(@RequestBody UserSignupDto userSignup) {
        userSignupService.saveUsersignup(userSignup);
        return;
    }
}
