package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserLoginDto;
import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserUpdateDto;
import com.skhu.luxuryshop.user.encoder.BCryptPasswordEncoder;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.NoUserFoundException;
import com.skhu.luxuryshop.user.exception.UnmatchedPasswordCheckException;
import com.skhu.luxuryshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDto findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return UserResponseDto.from(user.orElseThrow(NoUserFoundException::new));
    }

    public List<UserResponseDto> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoUserFoundException();
        }
        userRepository.deleteById(id);
    }

    public void update(UserUpdateDto userUpdateDto) {
        UserEntity updateUser = userUpdateDto.toUserEntity();
        UserEntity originUser = userRepository.findById(userUpdateDto.getId()).orElseThrow(NoUserFoundException::new);

        UserEntity user = UserEntity.builder()
                .id(updateUser.getId())
                .email(updateUser.getEmail())
                .password(passwordEncoder.encrypt(updateUser.getPassword()))
                .nickname(updateUser.getNickname())
                .carts(originUser.getCarts())
                .authorities(originUser.getAuthorities())
                .build();

        if (!originUser.getEmail().equals(userUpdateDto.getEmail())) {
            validateDuplicatedEmail(userUpdateDto.getEmail());
        }
        userRepository.save(user);
    }

    public void validateDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    public UserEntity login(UserLoginDto userLoginDto) {
        UserEntity user = userRepository.findOneWithAuthoritiesByEmail(userLoginDto.getEmail())
                .orElseThrow(NoUserFoundException::new);
        if (passwordEncoder.isMatch(userLoginDto.getPassword(), user.getPassword())) {
            return user;
        }
        throw new UnmatchedPasswordCheckException();
    }
}