package com.kyunghwan.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table @Getter @Setter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String id;

    @Column
    private String pwd;

    @OneToMany(mappedBy = "user")
    private ToDoList toDoList;

    @Builder
    public User(String id, String pwd, ToDoList toDoList) {
        this.id = id;
        this.pwd = pwd;
        this.toDoList = toDoList;
    }
}
