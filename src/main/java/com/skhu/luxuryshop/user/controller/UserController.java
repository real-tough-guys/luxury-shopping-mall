package com.skhu.luxuryshop.user.controller;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.service.UserSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserSignupService userSignupService;

    @GetMapping("/emails/{email}/exists")
    public ResponseEntity<Boolean> isDuplicatedEmail(@PathVariable String email) {
        userSignupService.validateDuplicatedEmail(email);
        return new ResponseEntity("중복되지 않은 이메일입니다.", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignupDto userSignupDto) {
        UserResponseDto savedUser = userSignupService.save(userSignupDto);
        return ResponseEntity
                .created(URI.create("/" + savedUser.getId()))
                .build();
    }
}