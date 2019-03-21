package com.kyunghwan.service;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public void loginCheck(Map<String, String> map) {

        String id = map.get("id");
        String pwd = map.get("pwd");

        User user = userRepository.findById(id);

        if (user == null) {
            System.out.println("로그인 실패. 아이디가 없거나 오류");
        } else {
            if (!user.getPwd().equals(pwd)) {
                System.out.println("로그인 실패. 비밀번호 불일치");
            } else {
                System.out.println("로그인 성공");
            }
        }
    }
}
