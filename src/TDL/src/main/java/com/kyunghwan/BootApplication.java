package com.kyunghwan;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.repository.ToDoListRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runner(ToDoListRepository toDoListRepository) throws Exception{
//        return args -> {
//            IntStream.rangeClosed(1, 3).forEach(index ->
//                    toDoListRepository.save(ToDoList.builder()
//                        .description("처음 배우는 스프링 부트2 를 이용한 TDL Project " + index)
//                        .status(true)
//                        .createdDate(LocalDateTime.now())
//                        .completedDate(LocalDateTime.now())
//                        .build()));
//        };
//    }
}
