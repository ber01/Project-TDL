package com.kyunghwan.service;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void pwdEncodingAndRegister(User requestUser) {
        User user = new User();
        user.setId(requestUser.getId());
        user.setEmail(requestUser.getEmail());
        user.setPwd(passwordEncoder.encode(requestUser.getPwd()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User user = userRepository.findById(id);

        if (user == null){
            throw new UsernameNotFoundException(id);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPwd(), authorities);
    }

    public User findCurrentUser(String username) {
        return userRepository.findById(username);
    }
}
