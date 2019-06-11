package com.kyunghwan.service;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class FoundService{

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private String id;
    private String certificationNumber;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("ToDoList 로그인 아이디입니다.");
        message.setText("이 메일주소는 발신전용 주소입니다. 회신이 불가능합니다.\n" + "회원님의 아이디는 [" + user.getId() + "] 입니다.");
        mailSender.send(message);
    }

    public boolean checkIdAndEmail(Map<String, String> map) throws Exception {
        String id = map.get("id");
        String email = map.get("email");
        User user = userRepository.findById(id);
        if (user == null){
            return false;
        } else if (!user.getEmail().equals(email)){
            return false;
        } else {
            return sendCertificationNumber(id, email);
        }
    }

    private boolean sendCertificationNumber(String id, String email){
        this.id = id;
        this.certificationNumber = createCertificationNumber();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("ToDoList 인증번호입니다.");
        message.setText("이 메일주소는 발신전용 주소입니다. 회신이 불가능합니다.\n" + "인증번호는 [" + certificationNumber + "] 입니다.");
        mailSender.send(message);

        return true;
    }

    private String createCertificationNumber(){
        String[] arr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
        Random random = new Random();
        StringBuilder certificationNumber = new StringBuilder();
        for (int i = 0; i < 10; i++){
            String s = String.valueOf(arr[random.nextInt(arr.length - 1)]);
            certificationNumber.append(s);
        }
        return String.valueOf(certificationNumber);
    }

    public boolean checkNumber(String certificationNumber) {
        return this.certificationNumber.equals(certificationNumber);
    }

    public boolean resetPassword(String pwd){
        String currentPwd = userRepository.findById(this.id).getPwd();
        if(!passwordEncoder.matches(pwd, currentPwd)){
            userService.updatePwd(this.id, pwd);
            return true;
        }
        return false;
    }
}
