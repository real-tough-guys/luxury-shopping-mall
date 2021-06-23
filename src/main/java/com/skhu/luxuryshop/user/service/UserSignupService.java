package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.encoder.BCryptPasswordEncoder;
import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserAuthority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.dto.UserSignupDto;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.repository.AuthorityRepository;
import com.skhu.luxuryshop.user.repository.UserAuthorityRepository;
import com.skhu.luxuryshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignupService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto save(UserSignupDto userSignupDto) throws Exception {
        if (!validateDuplicatedEmail(userSignupDto.getEmail())) {
            throw new DuplicatedEmailException();
        }

        UserEntity signupUser = userSignupDto.toUserEntity();
        UserEntity user = UserEntity.builder()
                .email(signupUser.getEmail())
                .password(passwordEncoder.encrypt(signupUser.getPassword()))
                .nickname(signupUser.getNickname())
                .address(signupUser.getAddress())
                .authorities(signupUser.getAuthorities())
                .build();

        Authority auth = authorityRepository.findById(2L).orElseThrow(Exception::new);
        UserAuthority userAuth = UserAuthority.builder()
                .user(user)
                .auth(auth)
                .build();

        userAuthorityRepository.save(userAuth);
        return UserResponseDto.from(userRepository.save(user));
    }

    public boolean validateDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return false;
        }
        return true;
    }
}