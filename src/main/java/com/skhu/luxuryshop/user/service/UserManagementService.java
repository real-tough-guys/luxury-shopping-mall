package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import com.skhu.luxuryshop.user.dto.UserUpdateDto;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.NoUserFoundException;
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
        if (!userRepository.existsById(userUpdateDto.getId())) {
            throw new NoUserFoundException();
        }
        UserEntity user = userUpdateDto.toUserEntity();
        userRepository.save(user);
    }
}