package com.kyunghwan.controller;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/forgot-id")
@RequiredArgsConstructor
public class IdForgotController {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @GetMapping
    public String getForgotId(){
        return "/found/id";
    }

    @PostMapping
    public ResponseEntity<?> postForgotId(@RequestBody Map<String, String> map){

        String email = map.get("email");

        User user = userRepository.findByEmail(email);

        if (email.equals("")){
            return new ResponseEntity<>("이메일을 입력하세요.", HttpStatus.BAD_REQUEST);
        } else if (user == null){
            return new ResponseEntity<>("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("ToDoList 로그인 아이디 입니다.");
        simpleMailMessage.setText(user.getId());

        mailSender.send(simpleMailMessage);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
