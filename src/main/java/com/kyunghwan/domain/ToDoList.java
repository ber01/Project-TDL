package com.kyunghwan.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table @Getter @Setter
@NoArgsConstructor
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    @NotBlank
    @Length(max = 35)
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "toDoList")
    private List<Comment> commentList = new ArrayList<>();

    public void statusUpdate() {
        this.status = !this.getStatus();
        this.completedDate = this.status ? LocalDateTime.now() : null;
    }

    public void update(String description) {
        this.description = description;
        this.status = this.getStatus();
        this.createdDate = this.getCreatedDate();
        this.completedDate = this.getCompletedDate();
    }

    public void add(Comment comment){
        comment.setToDoList(this);
        getCommentList().add(comment);
    }
}

