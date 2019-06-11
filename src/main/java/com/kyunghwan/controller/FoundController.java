package com.kyunghwan.controller;

import com.kyunghwan.domain.PasswordDto;
import com.kyunghwan.domain.User;
import com.kyunghwan.service.FoundService;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/found")
public class FoundController {

    private final FoundService foundService;

    // ID 찾기 화면
    @GetMapping("/id")
    public String getId(){
        return "/found/id";
    }

    // Password 찾기 화면
    @GetMapping("/pwd")
    public String getPwd(){
        return "/found/pwd";
    }

    // ID 찾기 -> 이메일 전송
    @PostMapping("/send/email")
    public ResponseEntity<?> postSendEmail(@RequestBody String email){
        User user = foundService.findUserByEmail(email);
        if (user == null) return new ResponseEntity<>("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST);
        foundService.sendEmail(user);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // Password 찾기 -> 아이디, 이메일 전송
    @PostMapping("/send/id-email")
    public ResponseEntity<?> postSendIdAndEmail(@RequestBody Map<String, String> map) throws Exception {
        return foundService.checkIdAndEmail(map)? new ResponseEntity<>("{}", HttpStatus.OK) : new ResponseEntity<>("아이디 또는 이메일을 다시 확인하세요.", HttpStatus.BAD_REQUEST);
    }

    // 인증번호 화면
    @GetMapping("/number")
    public String getNumber(){
        return "/found/number";
    }

    // 인증번호 전송
    @PostMapping("/send/number")
    public ResponseEntity<?> postSendNumber(@RequestBody String number){
        return foundService.checkNumber(number) ? new ResponseEntity<>("{}", HttpStatus.OK) : new ResponseEntity<>("올바르지 않은 인증번호 입니다.", HttpStatus.BAD_REQUEST);
    }

    // Password 초기화 화면
    @GetMapping("/reset")
    public String getReset(){
        return "/found/reset";
    }

    // 초기화 된 Password 전송
    @PostMapping("/send/reset")
    public ResponseEntity<?> postSendReset(@Valid @RequestBody PasswordDto pwd, BindingResult bindingResult) throws BindException{
        if (bindingResult.hasErrors()){
            if (bindingResult.hasErrors()) throw new BindException(bindingResult);
        }
        return foundService.resetPassword(pwd.getPwd()) ? new ResponseEntity<>("{}", HttpStatus.OK) : new ResponseEntity<>("이 전과 같은 비밀번호는 사용할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
