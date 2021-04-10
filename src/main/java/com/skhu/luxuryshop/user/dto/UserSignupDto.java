package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.SignupPasswordUnmatchedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserSignupDto {
    @NotNull(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @NotNull(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String password;

    @NotNull(message = "비밀번호를 한 번 더 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String passwordCheck;

    @NotNull(message = "닉네임을 입력하세요.")
    @Length(min = 2, max = 8, message = "2~8자리의 닉네임을 입력하세요.")
    private String nickname;

    public UserEntity toUserEntity() {
        validateSamePassword(password, passwordCheck);
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
        return user;
    }

    //비밀번호 일치 불일치 검사
    public void validateSamePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new SignupPasswordUnmatchedException("비밀번호 불일치");
        }
    }
}