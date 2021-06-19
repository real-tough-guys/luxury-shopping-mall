package com.skhu.luxuryshop.user.dto;

import com.skhu.luxuryshop.user.entity.UserEntity;
import com.skhu.luxuryshop.user.exception.UnmatchedPasswordCheckException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDto {
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식을 확인하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String password;

    @NotBlank(message = "비밀번호를 한 번 더 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String passwordCheck;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Length(min = 2, max = 8, message = "2~8자리의 닉네임을 입력하세요.")
    private String nickname;

    @NotBlank(message = "주소지를 입력하세요.")
    private String address;

    public UserEntity toUserEntity() {
        validateSamePassword(password, passwordCheck);

        UserEntity user = UserEntity.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .address(address)
                .build();
        return user;
    }

    public void validateSamePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new UnmatchedPasswordCheckException();
        }
    }
}