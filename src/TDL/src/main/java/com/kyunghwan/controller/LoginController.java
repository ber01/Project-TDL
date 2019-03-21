package com.kyunghwan.controller;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import com.kyunghwan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping
    public String login(){
        return "/login";
    }

    @PostMapping
    public ResponseEntity<?> test(@RequestBody Map<String, String> map){
        loginService.loginCheck(map);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
