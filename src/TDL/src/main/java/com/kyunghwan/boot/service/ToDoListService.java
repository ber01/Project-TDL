package com.kyunghwan.boot.service;

import com.kyunghwan.boot.domain.ToDoList;
import com.kyunghwan.boot.domain.User;
import com.kyunghwan.boot.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoListService {

    private final ToDoListRepository toDoListRepository;
    public ToDoListService(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }


    public void postList(ToDoList toDoList, User user) {
        toDoList.setStatus(false);
        toDoList.setCreatedDate(LocalDateTime.now());
        user.add(toDoList);
        toDoListRepository.save(toDoList);
    }

    public void deleteList(Integer idx) {
        toDoListRepository.deleteById(idx);
    }

    public void statusList(Integer idx) {
        ToDoList statusToDo = toDoListRepository.getOne(idx);
        statusToDo.statusUpdate();
        toDoListRepository.save(statusToDo);
    }

    public void updateList(Integer idx, String description) {
        ToDoList updateToDo = toDoListRepository.getOne(idx);
        updateToDo.update(description);
        toDoListRepository.save(updateToDo);
    }

    public List<ToDoList> findCurrentUserToDoList(User currentUser) {
        return toDoListRepository.findByUserOrderByIdx(currentUser);
    }
}
