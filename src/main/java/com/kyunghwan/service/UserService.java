package com.kyunghwan.service;

import com.kyunghwan.domain.User;
import com.kyunghwan.domain.UserDto;
import com.kyunghwan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void pwdEncodingAndRegister(UserDto userDto) {
        userRepository.save(userDto.toEntity(passwordEncoder.encode(userDto.getPwd())));
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User user = userRepository.findById(id);

        if (user == null){
            throw new UsernameNotFoundException(id);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPwd(), authorities);
    }

    public User findCurrentUser(String username) {
        return userRepository.findById(username);
    }
}
