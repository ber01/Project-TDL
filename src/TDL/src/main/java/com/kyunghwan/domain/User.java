package com.kyunghwan.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table @Getter @Setter
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<ToDoList> toDoList = new ArrayList<>();

    public void add(ToDoList toDoList){
        toDoList.setUser(this);
        getToDoList().add(toDoList);
    }
}
