package com.kyunghwan;

import com.kyunghwan.domain.User;
import com.kyunghwan.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository) throws Exception{
        return (args) -> {
            userRepository.save(User.builder().id("테스트 아이디").email("테스트 이메일").pwd("1234").build());
        };
    }
}
