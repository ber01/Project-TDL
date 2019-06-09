package com.kyunghwan.service;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FoundService{

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final UserService userService;

    private StringBuilder number;
    private String id;
    private String email;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void sendMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("회신이 불가능한 메일입니다.");
        message.setText(user.getId());
        mailSender.send(message);
    }

    public boolean forgotPwd(Map<String, String> map) throws Exception {
        System.out.println(number);
        User user = userRepository.findById(map.get("id"));
        if (user == null){
            return false;
        } else if (!user.getEmail().equals(map.get("email"))){
            return false;
        } else {
            this.id = map.get("id");
            this.email = map.get("email");
            return sendCertificationNumber();
        }
    }

    private boolean sendCertificationNumber() throws Exception {
        String[] arr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
        Random random = new Random();
        this.number = new StringBuilder();
        for (int i = 0; i < 10; i++){
            String s = String.valueOf(arr[random.nextInt(62)]);
            number.append(s);
        }
        System.out.println(number);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(this.email);
        message.setSubject("회신이 불가능한 메일입니다.");
        message.setText(String.valueOf(number));
        mailSender.send(message);

        return true;
    }

    public boolean checkNumber(String num) {
        return String.valueOf(this.number).equals(num);
    }

    public void resetPassword(String pwd){
        userService.updatePwd(this.id, pwd);
    }
}
