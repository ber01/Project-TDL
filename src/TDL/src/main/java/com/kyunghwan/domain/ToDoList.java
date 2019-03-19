package com.kyunghwan.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table @Getter @Setter
@NoArgsConstructor
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @ManyToOne
    private User user;

    @Builder
    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate, User user) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "idx=" + idx +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", completedDate=" + completedDate +
                '}';
    }

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
}

