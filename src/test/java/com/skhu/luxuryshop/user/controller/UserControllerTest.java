package com.skhu.luxuryshop.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.luxuryshop.user.dto.*;
import com.skhu.luxuryshop.user.entity.Authority;
import com.skhu.luxuryshop.user.entity.UserAuthority;
import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.DuplicatedEmailException;
import com.skhu.luxuryshop.user.exception.NoUserFoundException;
import com.skhu.luxuryshop.user.exception.UnmatchedPasswordCheckException;
import com.skhu.luxuryshop.user.jwt.TokenProvider;
import com.skhu.luxuryshop.user.service.UserManagementService;
import com.skhu.luxuryshop.user.service.UserSignupService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureWebMvc
public class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserSignupService userSignupService;
    @MockBean
    private UserManagementService userManagementService;
    @MockBean
    private TokenProvider tokenProvider;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_HEADER = "Bearer ";
    protected static final String AUTHORITIES_KEY = "auth";
    protected static final String AUTHORITIES_SPLITTER = ", ";
    private final String secret = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";


    private UserSignupDto normalUserSignup;
    private UserSignupDto duplicatedEmailUserSignup;
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

    private Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    private String token = createToken(existentUser);
    private Claims tokenData = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

    @BeforeEach
    void setUp() {
        normalUserSignup = new UserSignupDto("test123@gmail.co.kr", "password", "password", "홍길동");
        duplicatedEmailUserSignup = new UserSignupDto("test123@gmail.com", "password", "password", "홍길동");
    }

    @DisplayName("isDuplicatedEmail_중복되지 않은 이메일 요청인 경우 Ok")
    @Test
    void test_isDuplicatedEmail_unDuplicatedEmail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/users/emails/" + normalUserSignup.getEmail() + "/exists"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        boolean isDuplicatedEmail = Boolean.parseBoolean(body);
        assertThat(isDuplicatedEmail).isFalse();
    }

    @DisplayName("isDuplicatedEmail_중복된 이메일 요청인 경우 BadRequest")
    @Test
    void test_isDuplicatedEmail_duplicatedEmail() throws Exception {
        doThrow(new DuplicatedEmailException())
                .when(userSignupService)
                .validateDuplicatedEmail(duplicatedEmailUserSignup.getEmail());

        MvcResult mvcResult = mockMvc.perform(get("/api/users/emails/" + duplicatedEmailUserSignup.getEmail() + "/exists"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat("중복된 이메일입니다.").isEqualTo(body);
    }

    @DisplayName("signUp_정상 유저인 경우 isCreated")
    @Test
    void test_signUp_normalUser() throws Exception {
        UserResponseDto normalUserResponse = new UserResponseDto(1L, normalUserSignup.getEmail(), normalUserSignup.getNickname(), Collections.singletonList(userAuthority));
        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenReturn(normalUserResponse);

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(normalUserSignup)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        assertThat("/" + normalUserResponse.getId()).isEqualTo(location);
    }

    @DisplayName("signUp_중복된 이메일을 가진 유저인 경우 BadRequest")
    @Test
    void test_signUp_duplicatedEmailUser() throws Exception {
        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenThrow(new DuplicatedEmailException());

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicatedEmailUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat("중복된 이메일입니다.").isEqualTo(body);
    }

    @DisplayName("signUp_비밀번호 불일치 유저인 경우 BadRequest")
    @Test
    void test_signUp_unmatchedPasswordUser() throws Exception {
        UserSignupDto unmatchedPwdUserSignup = new UserSignupDto("test123@gmail.com", "password", "wrongpwd", "홍길동");
        when(userSignupService.save(ArgumentMatchers.any(UserSignupDto.class)))
                .thenThrow(new UnmatchedPasswordCheckException());

        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unmatchedPwdUserSignup)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat("비밀번호가 일치하지 않습니다.").isEqualTo(body);
    }

    @DisplayName("details_존재하는 유저의 id인 경우")
    @Test
    void test_details_existentUserId() throws Exception {
        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());
        doReturn(UserResponseDto.from(existentUser))
                .when(userManagementService)
                .findById(existentUser.getId());

        MvcResult mvcResult = mockMvc.perform(get("/api/users/" + existentUser.getId() + "/details")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        UserResponseDto userResponseDto = objectMapper.readValue(body, UserResponseDto.class);

        assertThat(existentUser.getEmail()).isEqualTo(userResponseDto.getEmail());
        assertThat(existentUser.getNickname()).isEqualTo(userResponseDto.getNickname());
    }

    @DisplayName("details_존재하지 않는 유저의 id인 경우 throw NoUserFoundException")
    @Test
    void test_details_nonExistentUserId() throws Exception {
        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());
        doThrow(new NoUserFoundException())
                .when(userManagementService)
                .findById(ArgumentMatchers.any());

        MvcResult mvcResult = mockMvc.perform(get("/api/users/" + 999L + "/details")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat("해당 유저가 존재하지 않습니다.").isEqualTo(body);
    }


    @DisplayName("delete_존재하는 유저의 id인 경우 NoContent")
    @Test
    void test_delete_existentUserId() throws Exception {
        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());

        mockMvc.perform(delete("/api/users/" + existentUser.getId() + "/delete")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("delete_존재하지 않는 유저의 id인 경우 throw NoUserFoundException")
    @Test
    void test_delete_nonExistentUserId() throws Exception {
        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());
        doThrow(new NoUserFoundException())
                .when(userManagementService)
                .deleteById(999L);

        MvcResult mvcResult = mockMvc.perform(delete("/api/users/" + 999 + "/delete")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat("해당 유저가 존재하지 않습니다.").isEqualTo(body);
    }


    @DisplayName("update_존재하는 유저인 경우")
    @Test
    void test_update_existentUser() throws Exception {
        UserUpdateDto existentUserUpdate = new UserUpdateDto(this.existentUser.getId(), this.existentUser.getEmail(), "changePwd", "changePwd", "newNick");
        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());

        mockMvc.perform(put("/api/users/update")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existentUserUpdate)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("update_존재하지 않는 유저인 경우 throw NoUserFoundException")
    @Test
    void test_update_nonExistentUser() throws Exception {
        UserUpdateDto nonExistentUserUpdate = new UserUpdateDto(999L, "email@gmail.com", "changePwd", "changePwd", "newNick");
        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());
        doThrow(new NoUserFoundException())
                .when(userManagementService)
                .update(ArgumentMatchers.any(UserUpdateDto.class));

        MvcResult mvcResult = mockMvc.perform(put("/api/users/update")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistentUserUpdate)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat("해당 유저가 존재하지 않습니다.").isEqualTo(body);
    }

    @DisplayName("update_비밀번호가 불일치인 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_update_unmatchedPasswordUser() throws Exception {
        UserUpdateDto unmatchedPwdUserUpdate = new UserUpdateDto(existentUser.getId(), existentUser.getEmail(), "changePwd", "wrongPwd", "newNick");
        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());
        doThrow(new UnmatchedPasswordCheckException())
                .when(userManagementService)
                .update(ArgumentMatchers.any(UserUpdateDto.class));

        MvcResult mvcResult = mockMvc.perform(put("/api/users/update")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unmatchedPwdUserUpdate)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        assertThat("비밀번호가 일치하지 않습니다.").isEqualTo(body);
    }


    @DisplayName("findAll_모든 유저 조회")
    @Test
    void test_findAll() throws Exception {
        List<UserResponseDto> users = new ArrayList<>();
        UserResponseDto userResponseDto1 = new UserResponseDto(1L, "test1@gmail.com", "nick", Collections.singletonList(userAuthority));
        UserResponseDto userResponseDto2 = new UserResponseDto(2L, "test2@gmail.com", "nick", Collections.singletonList(userAuthority));
        users.add(userResponseDto1);
        users.add(userResponseDto2);

        doReturn(tokenData)
                .when(tokenProvider)
                .getData(ArgumentMatchers.any());
        doReturn(users)
                .when(userManagementService)
                .findAll();

        MvcResult mvcResult = mockMvc.perform(get("/api/users/list")
                .header(AUTHORIZATION_HEADER, TOKEN_HEADER + token))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        List<UserResponseDto> userResponseList = objectMapper.readValue(body, new TypeReference<List<UserResponseDto>>() {
        });
        assertThat(users.size()).isEqualTo(userResponseList.size());
        assertThat(userResponseDto1.getEmail()).isEqualTo(userResponseList.get(0).getEmail());
        assertThat(userResponseDto2.getEmail()).isEqualTo(userResponseList.get(1).getEmail());
    }

    @DisplayName("login_정상 유저일 경우")
    @Test
    void test_login_normalUser() throws Exception {
        UserLoginDto normalUserLogin = new UserLoginDto(existentUser.getEmail(), existentUser.getPassword());
        doReturn(token)
                .when(tokenProvider)
                .createToken(ArgumentMatchers.any());
        doReturn(existentUser)
                .when(userManagementService)
                .login(ArgumentMatchers.any(UserLoginDto.class));

        MvcResult mvsResult = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(normalUserLogin)))
                .andExpect(status().isOk())
                .andExpect(header().stringValues(AUTHORIZATION_HEADER, TOKEN_HEADER + token))
                .andDo(print())
                .andReturn();

        String body = mvsResult.getResponse().getContentAsString();
        UserTokenDto bodyObject = objectMapper.readValue(body, UserTokenDto.class);

        assertThat(bodyObject.getId()).isEqualTo(existentUser.getId());
        assertThat(bodyObject.getToken()).isEqualTo(token);
    }

    @DisplayName("login_미등록 유저일 경우 throw NoUserFoundException")
    @Test
    void test_login_unRegisteredUser() throws Exception {
        UserLoginDto nonExistsUserLogin = new UserLoginDto(normalUserSignup.getEmail(), normalUserSignup.getPassword());
        doReturn(true)
                .when(tokenProvider)
                .validateToken(ArgumentMatchers.any());
        doReturn(token)
                .when(tokenProvider)
                .createToken(ArgumentMatchers.any());
        doThrow(NoUserFoundException.class)
                .when(userManagementService)
                .login(ArgumentMatchers.any(UserLoginDto.class));

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistsUserLogin)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("login_비밀번호가 일치하지 않는 경우 throw UnmatchedPasswordCheckException")
    @Test
    void test_login_unMatchedPasswordUser() throws Exception {
        UserLoginDto nonExistsUserLogin = new UserLoginDto(existentUser.getEmail(), existentUser.getPassword() + "1");

        doReturn(token)
                .when(tokenProvider)
                .createToken(ArgumentMatchers.any());
        doThrow(UnmatchedPasswordCheckException.class)
                .when(userManagementService)
                .login(ArgumentMatchers.any(UserLoginDto.class));

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistsUserLogin)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    public String createToken(UserEntity user) {
        List<Authority> userAuthorities = new ArrayList<>();
        for (UserAuthority userAuthority : user.getAuthorities()) {
            userAuthorities.add(userAuthority.getAuth());
        }
        String authorities = userAuthorities.stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.joining(AUTHORITIES_SPLITTER));

        long now = (new Date()).getTime();
        Date validity = new Date(now + 600000);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }
}