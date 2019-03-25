package com.kyunghwan.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table @Getter @Setter @ToString
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String id;

    @Column
    private String email;

    @Column
    private String pwd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ToDoList> toDoList;

    @Builder
    public User(String id, String email, String pwd) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
    }
}
