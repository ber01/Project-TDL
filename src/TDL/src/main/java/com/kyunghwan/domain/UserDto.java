package com.kyunghwan.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor
@ToString
public class UserDto {

    @NotBlank(message = "아이디를 입력하세요.")
    private String id;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식에 맞게 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String pwd;

    public User toEntity(String pwd) {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setPwd(pwd);
        return user;
    }
}
