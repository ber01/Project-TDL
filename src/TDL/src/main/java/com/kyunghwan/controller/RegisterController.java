package com.kyunghwan.controller;

import com.kyunghwan.domain.User;
import com.kyunghwan.service.RegisterService;
import com.kyunghwan.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final RegisterService registerService;

    public RegisterController(UserService userService, RegisterService registerService) {
        this.userService = userService;
        this.registerService = registerService;
    }

    @GetMapping
    public String register(){
        return "/register";
    }

    @PostMapping
    public ResponseEntity<?> postRegister(@Valid @RequestBody User user, BindingResult bindingResult){
        System.out.println("postRegister 진입 ");
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        userService.pwdEncodingAndRegister(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/duplication")
    public ResponseEntity<?> idDuplication(@RequestBody String id){
        return registerService.duplicationCheck(id) ? new ResponseEntity<>("{}", HttpStatus.OK) : new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
    }
}
