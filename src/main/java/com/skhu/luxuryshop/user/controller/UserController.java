package com.skhu.luxuryshop.user.controller;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.dto.UserUpdateDto;
import com.skhu.luxuryshop.user.service.UserManagementService;
import com.skhu.luxuryshop.user.service.UserSignupService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserResponseDto> details(@PathVariable Long id) {
        UserResponseDto userDetails = userManagementService.findById(id);
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userManagementService.deleteById(id);
        return new ResponseEntity("계정이 삭제되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userManagementService.update(userUpdateDto);
        return new ResponseEntity("수정 성공", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = userManagementService.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }
}