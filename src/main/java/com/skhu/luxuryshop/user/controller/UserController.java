package com.skhu.luxuryshop.user.controller;

import com.skhu.luxuryshop.user.dto.*;
import com.skhu.luxuryshop.user.jwt.AuthInterceptor;
import com.skhu.luxuryshop.user.jwt.TokenProvider;
import com.skhu.luxuryshop.user.service.UserManagementService;
import com.skhu.luxuryshop.user.service.UserSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserSignupService userSignupService;
    private final UserManagementService userManagementService;
    private final TokenProvider tokenProvider;

    @GetMapping("/emails/{email}/exists")
    public ResponseEntity<Boolean> isDuplicatedEmail(@PathVariable String email) {
        return new ResponseEntity(userSignupService.validateDuplicatedEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignupDto userSignupDto) throws Exception {
        UserResponseDto savedUser = userSignupService.save(userSignupDto);
        return ResponseEntity
                .created(URI.create("/" + savedUser.getId()))
                .build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<UserResponseDto> details(@PathVariable Long id) {
        UserResponseDto userDetails = userManagementService.findById(id);
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<UserResponseDto> details() {
        UserResponseDto userDetails = userManagementService.findByLoginUser();
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userManagementService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteByAdmin(@PathVariable Long id) {
        userManagementService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete() {
        userManagementService.deleteByLoginUser();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userManagementService.update(userUpdateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = userManagementService.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@Valid @RequestBody UserLoginDto userLoginDto) {


        String jwt = tokenProvider.createToken(userManagementService.login(userLoginDto));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AuthInterceptor.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new UserTokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        return new ResponseEntity(HttpStatus.OK);
    }
}