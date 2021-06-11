package com.skhu.luxuryshop.user.encoder;

import com.skhu.luxuryshop.user.dto.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BCryptPasswordEncoderTest {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String originPassword = "password";
    String encryptedPassword = "$2a$10$MafzWKqMEq8R1iYIydZ7nOu02R7DwSKdf5bEuqbimtu/mt8eM3N/q";

    @DisplayName("findById_존재하는 유저의 id인 경우")
    @Test
    void test_encrypt() {
        String encryptedPassword = passwordEncoder.encrypt(originPassword);

        assertThat(true).isEqualTo(passwordEncoder.isMatch(originPassword, encryptedPassword));
    }

    @DisplayName("isMatch_비밀번호가 일치하는 경우")
    @Test
    void test_isMatch_normalPassword() {
        assertThat(true).isEqualTo(passwordEncoder.isMatch(originPassword, encryptedPassword));
    }

    @DisplayName("isMatch_비밀번호가 일치하지 않는 경우")
    @Test
    void test_isMatch_wrongPassword() {
        assertThat(false).isEqualTo(passwordEncoder.isMatch("wrongPwd", encryptedPassword));
    }
}
