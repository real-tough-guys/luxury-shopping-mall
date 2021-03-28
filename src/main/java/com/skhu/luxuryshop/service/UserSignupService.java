package com.skhu.luxuryshop.service;

import com.skhu.luxuryshop.dto.UserSignupDto;
import com.skhu.luxuryshop.entity.User;
import com.skhu.luxuryshop.mapper.UserMapper;
import com.skhu.luxuryshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSignupService {
    @Autowired
    private UserRepository userRepository;

    UserMapper userMapper = UserMapper.INSTANCE;

    //UserSignUp객체를 DB에 저장
    public void saveUsersignup(UserSignupDto userSignup) {
        if (verifyEmail(userSignup.getEmail()) && verifyPassword(userSignup)) {
            User newUser = userMapper.userSignupDtoToUser(userSignup);
            userRepository.save(newUser);
        }
    }

    //Email 중복검사
    public boolean verifyEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return true;
        return false;
    }

    //비밀번호, 비밀번호 확인 일치 여부 검증
    public boolean verifyPassword(UserSignupDto userSignup) {
        //비밀번호
        String password = userSignup.getPassword();
        //확인 비밀번호
        String passwordCheck = userSignup.getPasswordCheck();

        if (password.equals(passwordCheck))
            return true;
        return false;
    }
}
