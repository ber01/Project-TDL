package com.kyunghwan.controller;

import com.kyunghwan.domain.PasswordResetToken;
import com.kyunghwan.domain.User;
import com.kyunghwan.repository.PasswordResetTokenRepository;
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

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class PasswordForgotController {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;

    @GetMapping
    public String getForgotPassword(){
        return "/found/pwd";
    }

    @PostMapping
    public ResponseEntity<?> postForgotPassword(@RequestBody Map<String, String> map, HttpServletRequest httpServletRequest){
        System.out.println(map);

        String id = map.get("id");
        String email = map.get("email");

        User user = userRepository.findById(id);

        if (id.equals("")){
            return new ResponseEntity<>("아이디를 입력하세요.", HttpStatus.BAD_REQUEST);
        } else if (email.equals("")){
            return new ResponseEntity<>("이메일을 입력하세요.", HttpStatus.BAD_REQUEST);
        } else {
            if (user == null){
                return new ResponseEntity<>("존재하지 않는 아이디입니다.", HttpStatus.BAD_REQUEST);
            } else {
                String userEmail = user.getEmail();
                if (!email.equals(userEmail)){
                    return new ResponseEntity<>("이메일이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
                }
            }
        }

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);

        if (passwordResetToken != null){
            System.out.println(passwordResetToken);
            passwordResetTokenRepository.delete(passwordResetToken);
        }

        passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(5);
        passwordResetTokenRepository.save(passwordResetToken);

        String url = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
        String resetUrl = url + "/reset-password?token=" + passwordResetToken.getToken();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("ToDoList 비밀번호 변경 링크입니다.");
        simpleMailMessage.setText(resetUrl);

        mailSender.send(simpleMailMessage);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
