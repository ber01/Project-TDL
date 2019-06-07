package com.kyunghwan.controller;

import com.kyunghwan.domain.User;
import com.kyunghwan.service.FoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/found")
public class FoundController {

    private final FoundService foundService;

    @GetMapping("/id")
    public String id(){
        return "/found/id";
    }

    @GetMapping("/pwd")
    public String pwd(){
        return "/found/pwd";
    }

    @PostMapping("/send/email")
    public ResponseEntity<?> test(@RequestBody String email){
        User user = foundService.findUserByEmail(email);
        System.out.println(user);
        if (user == null) return new ResponseEntity<>("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
