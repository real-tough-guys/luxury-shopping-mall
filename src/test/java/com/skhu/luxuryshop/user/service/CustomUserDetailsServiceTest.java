package com.skhu.luxuryshop.user.service;

import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class CustomUserDetailsServiceTest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntity;
    private Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

    @BeforeEach
    void setUp(){
        userEntity = new UserEntity(3L, "test1@gmail.com", "password", "홍길동", Collections.singleton(authority));
        userRepository.save(userEntity);
    }

    @DisplayName("loadUserByUsername_존재하는 유저일 경우")
    @Test
    void test_loadUserByUsername_existsUser(){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEntity.getEmail());

        assertThat(userDetails.getUsername()).isEqualTo(userEntity.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(userEntity.getPassword());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        authorities.stream()
                .forEach((authority) -> assertThat(userEntity.getAuthorities().contains(authority)));
    }

    @DisplayName("loadUserByUsername_존재하지 않는 유저일 경우 throw UsernameNotFoundException")
    @Test
    void test_loadUserByUsername_unExistsUser(){
        String unExistsEmail = "unExistsEmail@gmail.co.kr";
        assertThatThrownBy(()->
                customUserDetailsService.loadUserByUsername(unExistsEmail))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(unExistsEmail+"유저가 존재하지 않습니다.");
    }
}