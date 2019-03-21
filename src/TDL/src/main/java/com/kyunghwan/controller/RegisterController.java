package com.kyunghwan.controller;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String register(){
        return "/register";
    }

    @PostMapping
    public ResponseEntity<?> test(@RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
