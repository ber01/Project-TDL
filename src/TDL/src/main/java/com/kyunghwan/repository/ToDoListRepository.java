package com.kyunghwan.repository;

import com.kyunghwan.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    public List<ToDoList> findAllByOrderByIdx();
}
