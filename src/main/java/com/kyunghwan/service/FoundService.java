package com.kyunghwan.service;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoundService{

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

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
}
