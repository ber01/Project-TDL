package com.kyunghwan.controller;

import com.kyunghwan.repository.ToDoListRepository;
import com.kyunghwan.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
