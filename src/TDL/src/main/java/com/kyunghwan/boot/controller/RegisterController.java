package com.kyunghwan.boot.controller;

import com.kyunghwan.boot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String register() {
        return "/register";
    }

    @PostMapping
    public ResponseEntity<?> postRegister(@RequestBody Map<String, String> map) {
        userService.pwdEncoding(map);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
}
