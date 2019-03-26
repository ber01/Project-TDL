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

import java.util.List;
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
        model.addAttribute("tdlList", toDoListService.findTdlList(idx));

        System.out.println("현재 user 의 idx 값 : " + user.getIdx());
        List<ToDoList> toDoLists = user.getToDoList();

        if (toDoLists.isEmpty()){
            System.out.println("현재 등록 된 ToDo가 존재하지 않습니다.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (ToDoList t : toDoLists) {
                sb.append(t.getIdx()).append(" ").append(t.getDescription()).append(" ").append(t.getStatus()).append(" ").append(t.getCreatedDate()).append(" ").append(t.getCompletedDate()).append("\n");
            }
            System.out.println(sb.toString() + "=========================");
        }

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
