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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponseDto> details(@PathVariable Long id) {
        UserResponseDto userDetails = userManagementService.findById(id);
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ResponseEntity<UserResponseDto> details() {
        UserResponseDto userDetails = userManagementService.findByLoginUser();
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userManagementService.deleteById(id);
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteByAdmin(@PathVariable Long id) {
        userManagementService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> delete() {
        userManagementService.deleteByLoginUser();
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userManagementService.update(userUpdateDto);
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.OK);
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

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.OK);
    }
}