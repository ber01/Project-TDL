package com.kyunghwan.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table @Getter @Setter @ToString
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String content;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @ManyToOne
    private ToDoList toDoList;

    public void update(String content) {
        this.content = content;
        this.createdDate = this.getCreatedDate();
        this.modifiedDate = LocalDateTime.now();
    }
}
