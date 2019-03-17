package com.kyunghwan.controller;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tdl")
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("tdlList", toDoListService.findTdlList());
        return "/tdl/list";
    }

    @PostMapping
    public ResponseEntity<?> postTdl(@RequestBody ToDoList toDoList) {
        toDoListService.postList(toDoList);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteTdl(@PathVariable("idx") Integer idx){
        toDoListService.deleteList(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/complete/{idx}")
    public ResponseEntity<?> statusTdl(@PathVariable("idx") Integer idx){
        toDoListService.completeList(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
