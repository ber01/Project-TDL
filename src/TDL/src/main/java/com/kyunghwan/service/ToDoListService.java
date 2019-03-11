package com.kyunghwan.service;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<ToDoList> findTdlList(){
        return toDoListRepository.findAll();
    }
}
