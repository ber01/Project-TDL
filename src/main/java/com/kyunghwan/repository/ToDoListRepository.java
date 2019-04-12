package com.kyunghwan.repository;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    List<ToDoList> findByUserOrderByIdx(User user);

    ToDoList findByIdx(Integer tdlIdx);
}
