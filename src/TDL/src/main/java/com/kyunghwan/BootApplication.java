package com.kyunghwan;

import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ToDoListRepository toDoListRepository) throws Exception{
        return args -> {
            IntStream.rangeClosed(1, 20).forEach(index ->
                    toDoListRepository.save(ToDoList.builder()
                        .description("테스트 설명 " + index)
                        .status(true)
                        .createdDate(LocalDateTime.now())
                        .completedDate(LocalDateTime.now())
                        .build()));
        };
    }
}
