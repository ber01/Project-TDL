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

import java.util.Map;

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
    public ResponseEntity<?> sendEmail(@RequestBody String email) throws Exception{
        User user = foundService.findUserByEmail(email);
        if (user == null) return new ResponseEntity<>("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST);
        foundService.sendMail(user);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("/send/id")
    public ResponseEntity<?> sendIdAndEmail(@RequestBody Map<String, String> map) throws Exception {
        return foundService.forgotPwd(map)? new ResponseEntity<>("{}", HttpStatus.OK) : new ResponseEntity<>("아이디 또는 이메일을 다시 확인하세요.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/number")
    public String certificationNumber(){
        return "/found/number";
    }

    @PostMapping("/send/number")
    public ResponseEntity<?> checkNumber(@RequestBody String number){
        System.out.println(number);
        return foundService.checkNumber(number) ? new ResponseEntity<>("{}", HttpStatus.OK) : new ResponseEntity<>("올바르지 않은 인증번호 입니다.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/reset")
    public String getReset(){
        return "/found/reset";
    }

    @PostMapping("/send/reset")
    public ResponseEntity<?> resetPassword(@RequestBody String pwd){
        foundService.resetPassword(pwd);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
