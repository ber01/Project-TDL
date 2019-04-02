package com.kyunghwan.service;

import com.kyunghwan.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean duplicationCheck(String id) {
        return userRepository.findById(id) == null;
    }
}
