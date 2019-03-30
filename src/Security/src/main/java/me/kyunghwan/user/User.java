package me.kyunghwan.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    private String email;

    @Column
    private String password;

    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
