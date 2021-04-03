package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.FailedToSignupException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSignupService {
    private final UserRepository userRepository;

    //회원생성
    @Transactional
    public void save(UserSignupDto userSignupDto) {
        UserEntity user = userSignupDto.toUserEntity();
        if (!isDuplicatedEmail(userSignupDto.getEmail())) {
            userRepository.save(user);
            return;
        }
        throw new FailedToSignupException("회원가입 실패");
    }

    //Email 중복검사
    public boolean isDuplicatedEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new DuplicatedEmailException("중복된 이메일");
        }
        return false;
    }
}