package com.kyunghwan.domain;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter @NoArgsConstructor
@ToString
public class UserDto {

    @NotBlank
    @Pattern(regexp = "[a-z0-9]{5,10}")
    private String id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}")
    private String pwd;

    public User toEntity(String pwd) {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setPwd(pwd);
        return user;
    }
}
