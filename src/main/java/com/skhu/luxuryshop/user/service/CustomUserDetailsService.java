package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findOneWithAutoritiesByEmail(email)
                .map(userEntity -> createUser(email, userEntity))
                .orElseThrow(() -> new UsernameNotFoundException(email + "유저가 존재하지 않습니다."));
    }

    private User createUser(String email, UserEntity user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}