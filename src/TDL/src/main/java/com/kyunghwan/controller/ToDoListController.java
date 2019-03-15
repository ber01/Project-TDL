package com.kyunghwan.controller;

import com.kyunghwan.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
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
    public String postList(@RequestBody MultiValueMap<String, Object> data) {
        toDoListService.postList(data.get("test").get(0));
        return "redirect:/tdl/list";
    }
}
