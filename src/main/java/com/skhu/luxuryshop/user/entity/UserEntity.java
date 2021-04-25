package com.skhu.luxuryshop.user.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Length(min = 2, max = 8, message = "2~8자리의 닉네임을 입력하세요.")
    private String nickname;
}