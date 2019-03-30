package com.kyunghwan.boot.controller;

import com.kyunghwan.boot.domain.ToDoList;
import com.kyunghwan.boot.domain.User;
import com.kyunghwan.boot.service.ToDoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tdl")
public class ToDoListController {

    private final ToDoListService toDoListService;

    public ToDoListController(ToDoListService toDoListService){
        this.toDoListService = toDoListService;
    }

    private User user;

    @GetMapping("/list")
    public String list(){
        return "/tdl/list";
    }

    @PostMapping
    public ResponseEntity<?> postTdl(@RequestBody ToDoList toDoList) {
        toDoListService.postList(toDoList, this.user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteTdl(@PathVariable("idx") Integer idx){
        toDoListService.deleteList(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/complete/{idx}")
    public ResponseEntity<?> statusTdl(@PathVariable("idx") Integer idx){
        toDoListService.statusList(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> updateTdl(@PathVariable("idx") Integer idx, @RequestBody String description){
        toDoListService.updateList(idx, description);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
