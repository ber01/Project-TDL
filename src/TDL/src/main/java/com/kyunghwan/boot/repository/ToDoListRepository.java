package com.kyunghwan.boot.repository;

import com.kyunghwan.boot.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    List<ToDoList> findByUserIdx(Integer idx);
}
