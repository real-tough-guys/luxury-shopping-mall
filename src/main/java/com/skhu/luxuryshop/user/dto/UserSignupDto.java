package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserSignupDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 8, max = 20)
    private String password;

    @NotNull
    @Length(min = 8, max = 20)
    private String passwordCheck;

    @NotNull
    @Length(min = 2, max = 8)
    private String nickname;

    public UserEntity toUserEntity() {
        isSamePassword(password, passwordCheck);
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
        return user;
    }

    //비밀번호 일치 불일치 검사
    public boolean isSamePassword(String password, String passwordCheck) {
        if (password.equals(passwordCheck))
            return true;
        throw new SignupPasswordUnmatchedException("비밀번호 불일치");
    }
}