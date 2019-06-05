package com.kyunghwan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/found")
public class FoundController {

    @GetMapping("/id")
    public String id(){
        return "id";
    }

    @GetMapping("/pwd")
    public String pwd(){
        return "pwd";
    }
}
