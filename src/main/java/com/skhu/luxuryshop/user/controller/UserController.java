package com.skhu.luxuryshop.user.controller;

import com.skhu.luxuryshop.user.dto.*;
import com.skhu.luxuryshop.user.jwt.JwtFilter;
import com.skhu.luxuryshop.user.jwt.TokenProvider;
import com.skhu.luxuryshop.user.service.UserManagementService;
import com.skhu.luxuryshop.user.service.UserSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/emails/{email}/exists")
    public ResponseEntity<String> isDuplicatedEmail(@PathVariable String email) {
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

    @GetMapping("/{id}/details")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponseDto> details(@PathVariable Long id) {
        UserResponseDto userDetails = userManagementService.findById(id);
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<UserResponseDto> details() {
        UserResponseDto userDetails = userManagementService.findByLoginUser();
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userManagementService.deleteById(id);
        return new ResponseEntity("계정이 삭제되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> delete() {
        userManagementService.deleteByLoginUser();
        SecurityContextHolder.clearContext();
        return new ResponseEntity("계정이 삭제되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userManagementService.update(userUpdateDto);
        SecurityContextHolder.clearContext();
        return new ResponseEntity("정보를 변경했습니다. 다시 로그인하세요.", HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = userManagementService.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new UserTokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}