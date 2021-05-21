package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto save(UserSignupDto userSignupDto) {
        if (!validateDuplicatedEmail(userSignupDto.getEmail())) {
            throw new DuplicatedEmailException();
        }

        UserEntity signupUser = userSignupDto.toUserEntity();
        UserEntity user = UserEntity.builder()
                .email(signupUser.getEmail())
                .password(passwordEncoder.encode(signupUser.getPassword()))
                .nickname(signupUser.getNickname())
                .authorities(signupUser.getAuthorities())
                .build();

        return UserResponseDto.from(userRepository.save(user));
    }

    public boolean validateDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return false;
        }
        return true;
    }
}