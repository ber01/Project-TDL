package com.kyunghwan.controller;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.domain.User;
import com.kyunghwan.service.ToDoListService;
import com.kyunghwan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tdl")
public class ToDoListController {

    private final ToDoListService toDoListService;
    private final UserService userService;

    private User currentUser;

    @GetMapping("/list")
    public String list(Model model){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = userService.findCurrentUser(user.getUsername());
        model.addAttribute("tdlList", toDoListService.findCurrentUserTdl(currentUser));
        return "/tdl/list";
    }

    @PostMapping
    public ResponseEntity<?> postTdl(@RequestBody ToDoList toDoList) {
        toDoListService.postList(toDoList, currentUser);
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
