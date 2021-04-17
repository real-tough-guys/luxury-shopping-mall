package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignupService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto save(UserSignupDto userSignupDto) {
        validateDuplicatedEmail(userSignupDto.getEmail());
        UserEntity user = userSignupDto.toUserEntity();
        return UserResponseDto.from(userRepository.save(user));
    }

    public void validateDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException("중복된 이메일입니다.");
        }
    }
}