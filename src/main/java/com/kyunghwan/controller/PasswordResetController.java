package com.kyunghwan.controller;

import com.kyunghwan.domain.PasswordResetToken;
import com.kyunghwan.domain.ResetDto;
import com.kyunghwan.repository.PasswordResetTokenRepository;
import com.kyunghwan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/reset-password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserService userService;

    @GetMapping
    public String getResetController(@RequestParam(required = false) String token, Model model){

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null){
            model.addAttribute("error", "유효하지 않은 요청입니다.");
        } else if (passwordResetToken.isExpired()){
            model.addAttribute("error", "유효기간이 만료되었습니다.");
        } else{
            model.addAttribute("token", passwordResetToken.getToken());
        }

        return "/found/reset";
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> postResetController(@Valid @RequestBody ResetDto resetDto, BindingResult bindingResult) throws Exception{

        if (bindingResult.hasErrors()){
            String body = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
            if (resetDto.getPwd().equals("")){
                body = "비밀번호를 입력하세요.";
            }
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        userService.updatePwd(resetDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
