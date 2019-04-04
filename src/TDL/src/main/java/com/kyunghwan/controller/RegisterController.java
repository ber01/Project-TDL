package com.kyunghwan.controller;

import com.kyunghwan.domain.UserDto;
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
    public ResponseEntity<?> postRegister(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        userService.pwdEncodingAndRegister(userDto);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/duplication")
    public ResponseEntity<?> idDuplication(@RequestBody String id){
        return registerService.duplicationCheck(id) ? new ResponseEntity<>("{}", HttpStatus.OK) : new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
    }
}
