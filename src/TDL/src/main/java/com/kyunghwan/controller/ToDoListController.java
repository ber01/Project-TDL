package com.kyunghwan.controller;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.domain.User;
import com.kyunghwan.service.ToDoListService;
import com.kyunghwan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/tdl")
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;

    @Autowired
    UserService userService;

    private User user;

    @GetMapping("/list")
    public String list(Model model){
        if (this.user == null) return "redirect:/login";
        Integer idx = this.user.getIdx();
        System.out.println("현재 User idx 값 : " + idx);
        model.addAttribute("tdlList", toDoListService.findTdlList(idx));
        return "/tdl/list";
    }

    @PostMapping
    public ResponseEntity<?> postTdl(@RequestBody ToDoList toDoList) {
        toDoListService.postList(toDoList, this.user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/current")
    public ResponseEntity<?> test(@RequestBody Map<String, String> map){
        this.user = userService.findUser(map);
        return new ResponseEntity<>("{}", HttpStatus.OK);
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

    @GetMapping("/logout")
    public String logout(){
        this.user = null;
        return "redirect:/login";
    }
}
