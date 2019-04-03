package com.kyunghwan.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity @Table @Getter @Setter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(nullable = false)
    @NotEmpty(message = "아이디를 입력하세요.")
    private String id;

    @Column(nullable = false)
    @NotEmpty(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식에 맞게 입력하세요.")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String pwd;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<ToDoList> toDoList = new ArrayList<>();

    public void add(ToDoList toDoList){
        toDoList.setUser(this);
        getToDoList().add(toDoList);
    }
}