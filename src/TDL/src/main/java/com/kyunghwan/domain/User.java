package com.kyunghwan.domain;

import lombok.*;

import javax.persistence.*;

@Entity @Table @Getter @Setter @ToString
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String id;

    @Column
    private String pwd;

    @Builder
    public User(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }
}
