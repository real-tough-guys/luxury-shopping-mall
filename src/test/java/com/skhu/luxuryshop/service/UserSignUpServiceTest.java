package com.skhu.luxuryshop.service;


import com.skhu.luxuryshop.dto.UserSignupDto;
import com.skhu.luxuryshop.entity.User;
import com.skhu.luxuryshop.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserSignUpServiceTest {
    LinkedList<Integer> mockedList = mock(LinkedList.class);

    LinkedList<Integer> getMockedList() {
        return mockedList;
    }

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserSignupService userSignupService;

    //정상적인 UserSignupDto객체
    UserSignupDto userSignup1;
    //이미 존재하는 이메일, 서로 다른 비밀번호를 갖고있는 UsersignupDto객체
    UserSignupDto userSignup2;
    User user;

    @BeforeEach
    void prepare(){
        user = new User(999, "test123@gmail.com", "password", "홍길홍");
        userSignup1 = new UserSignupDto("test1@gmail.com", "password1", "password1", "홍길동");
        userSignup2 = new UserSignupDto("test123@gmail.com", "password2", "unmatch", "홍길덩");
    }

    //Email 중복검사 테스트
    @Test
    void test_verifyEmail(){
        when(userRepository.findByEmail(userSignup1.getEmail()))
                .thenReturn(null);
        when(userRepository.findByEmail(userSignup2.getEmail()))
                .thenReturn(this.user);


        //이메일이 중복되지 않는 dto의 이메일을 넣었을 때
        assertTrue(userSignupService.verifyEmail(userSignup1.getEmail()));
        //이메일이 중복되는 dto의 이메일을 넣었을 때
        assertFalse(userSignupService.verifyEmail(userSignup2.getEmail()));
    }

    //비밀번호, 비밀번호 확인 일치 여부 테스트
    @Test
    void test_verifyPassword(){
        assertTrue(userSignupService.verifyPassword(userSignup1));
        assertFalse(userSignupService.verifyPassword(userSignup2));
    }

    //User저장 테스트
    @Test
    void test_saveUserSignup(){
        when(userRepository.findByEmail(userSignup1.getEmail()))
                .thenReturn(null);
        when(userRepository.findByEmail(userSignup2.getEmail()))
                .thenReturn(this.user);
        when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(null);

        //userSignup2에 대해 save메소드가 호출되지 않았는지 확인
        userSignupService.saveUsersignup(userSignup2);
        verify(userRepository,never()).save(ArgumentMatchers.any(User.class));


        //userSignup1에 대해 save메소드가 호출되었는지 확인
        userSignupService.saveUsersignup(userSignup1);
        verify(userRepository).save(ArgumentMatchers.any(User.class));
    }
}
