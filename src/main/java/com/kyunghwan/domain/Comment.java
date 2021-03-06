package com.kyunghwan.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity @Table @Getter @Setter @ToString
@NoArgsConstructor
public class Comment implements Comparable<Comment> {

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

    public void register(Map<String, String> map, ToDoList toDoList) {
        this.setContent(map.get("content"));
        this.setCreatedDate(LocalDateTime.now());
        toDoList.add(this);
    }

    public void update(String content) {
        this.content = content;
        this.createdDate = this.getCreatedDate();
        this.modifiedDate = LocalDateTime.now();
    }

    @Override
    public int compareTo(Comment o) {
        return this.getIdx() < o.getIdx() ? -1 : 1;
    }
}
