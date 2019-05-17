package com.kyunghwan.controller;

import com.kyunghwan.domain.UserDto;
import com.kyunghwan.service.RegisterService;
import com.kyunghwan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final RegisterService registerService;

    @GetMapping
    public String register(){
        return "/register";
    }

    @PostMapping
    public ResponseEntity<?> postRegister(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) throw new BindException(bindingResult);

        userService.pwdEncodingAndRegister(userDto);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/duplication/id")
    public ResponseEntity<?> idDuplication(@RequestBody String id){
        return registerService.idDuplicationCheck(id) ? new ResponseEntity<>("사용가능한 아이디 입니다.", HttpStatus.OK) : new ResponseEntity<>("이미 사용중인 아이디 입니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/duplication/email")
    public ResponseEntity<?> emailDuplication(@RequestBody String email){
        return registerService.emailDuplicationCheck(email) ? new ResponseEntity<>("사용가능한 이메일 입니다.", HttpStatus.OK) : new ResponseEntity<>("이미 사용중인 이메일 입니다.", HttpStatus.BAD_REQUEST);
    }
}
