package com.kyunghwan.boot.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
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