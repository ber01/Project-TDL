package com.kyunghwan.repository;

import com.kyunghwan.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    public List<ToDoList> findAllByOrderByIdx();
}
