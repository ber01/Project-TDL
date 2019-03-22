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

    public boolean loginCheck(Map<String, String> map) {

        String id = map.get("id");
        String pwd = map.get("pwd");

        User user = userRepository.findById(id);

        if (user == null) return false;
        return user.getPwd().equals(pwd);
    }
}
