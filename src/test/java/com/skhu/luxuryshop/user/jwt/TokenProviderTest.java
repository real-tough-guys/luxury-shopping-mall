package com.skhu.luxuryshop.user.jwt;

import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserAuthority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class TokenProviderTest {
    protected static final String AUTHORITIES_KEY = "auth";
    protected static final String AUTHORITIES_SPLITTER = ", ";
    @Autowired
    private TokenProvider tokenProvider;

    private Authority authority = Authority.builder()
            .authorityName("ROLE_ADMIN")
            .build();
    private UserAuthority userAuthority = UserAuthority.builder()
            .auth(authority)
            .build();
    private UserEntity existentUser = UserEntity.builder()
            .id(1L)
            .email("test123@gmail.com")
            .password("password")
            .nickname("nick")
            .authorities(Collections.singletonList(userAuthority))
            .build();

    @DisplayName("createToken_토큰 생성 검사")
    @Test
    void test_createToken() {
        String token = tokenProvider.createToken(existentUser);
        Claims tokenData = tokenProvider.getData(token);
        String authorities = (String) tokenData.get(AUTHORITIES_KEY);
        String[] authoritiesArray = authorities.split(AUTHORITIES_SPLITTER);
        long now = (new Date()).getTime();
        Date nowDate = new Date(now + 3590000);

        assertThat(existentUser.getAuthorities().get(0).getAuth().getAuthorityName()).isEqualTo(authoritiesArray[0]);
        assertThat(existentUser.getEmail()).isEqualTo(tokenData.getSubject());
        assertThat(nowDate).isBefore(tokenData.getExpiration());
    }

    @DisplayName("validateToken_정상 토큰일 경우")
    @Test
    void test_validateToken_normalToken() {
        String token = tokenProvider.createToken(existentUser);
        assertThat(tokenProvider.validateToken(token)).isTrue();
    }

    @DisplayName("validateToken_잘못된 토큰일 경우")
    @Test
    void test_validateToken_expiredToken() {
        assertThatThrownBy(() -> {
                    tokenProvider.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MTIzQGdtYWlsLmNvbSIsImF1dGgiOiJST0xFX1VTRVIsIFJPTEVfQURNSU4iLCJleHAiOjE2MjM3NzExOTd9.bEEXpSi0ZhJn98vOmoCINkAGeZqEY-SjcgwXd88mGvrgNtZZVCnUZ6pYZknk7nzAnEB1dDO56do4lx3Yldp7CQ");
                }
        ).isInstanceOf(JwtException.class);
    }
}
