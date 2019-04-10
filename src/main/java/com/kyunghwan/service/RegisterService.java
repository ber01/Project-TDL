package com.kyunghwan.service;

import com.kyunghwan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    public boolean idDuplicationCheck(String id) {
        return userRepository.findById(id) == null;
    }

    public boolean emailDuplicationCheck(String email) {
        return userRepository.findByEmail(email) == null;
    }
}
