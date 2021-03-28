package com.skhu.luxuryshop.mapper;

import com.skhu.luxuryshop.dto.UserSignupDto;
import com.skhu.luxuryshop.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Mock
    User user;
    @Mock
    UserSignupDto userSignup;
    @InjectMocks
    UserMapper userMapper = UserMapper.INSTANCE;


    @BeforeEach
    void prepare() {
        user = new User(999, "test123@gmail.com", "password", "홍길홍");
        userSignup = new UserSignupDto("test1@gmail.com", "password1", "wrongpassword2", "홍길동");
    }

    //UserSignupDto => User 객체변환 테스트
    @Test
    void test_userSignupDtoToUser() {
        UserSignupDto newSignup = userMapper.userToUserSignupDto(user);

        //각 항목 검사
        assertEquals(newSignup.getEmail(), user.getEmail());
        assertEquals(newSignup.getPassword(), user.getPassword());
        assertEquals(newSignup.getNickname(), user.getNickname());
    }

    //User => UserSignupDto 객체변환 테스트
    @Test
    void test_userToUserSignupDto() {
        User newUser = userMapper.userSignupDtoToUser(userSignup);

        //각 항목 검사
        assertEquals(newUser.getEmail(), userSignup.getEmail());
        assertEquals(newUser.getPassword(), userSignup.getPassword());
        assertEquals(newUser.getNickname(), userSignup.getNickname());
    }
}
