package com.kyunghwan.service;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<ToDoList> findTdlList(){
        return toDoListRepository.findAllByOrderByIdx();
    }

    public void postList(ToDoList toDoList) {
        toDoList.setStatus(false);
        toDoList.setCreatedDate(LocalDateTime.now());
        toDoListRepository.save(toDoList);
    }

    public void deleteList(Integer idx) {
        toDoListRepository.deleteById(idx);
    }

    public void completeList(Integer idx) {
        ToDoList statusToDo = toDoListRepository.getOne(idx);
        statusToDo.updateStatus();
        toDoListRepository.save(statusToDo);
    }
}
