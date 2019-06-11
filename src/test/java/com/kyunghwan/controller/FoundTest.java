package com.kyunghwan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyunghwan.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoundTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        // MockMvc 초기화
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .alwaysDo(print())
                    .build();

        // User 등록
        UserDto userDto = new UserDto();
        userDto.setId("ber01");
        userDto.setEmail("ber01@naver.com");
        userDto.setPwd("test123!");

        mockMvc
                .perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void get_ID_찾기_요청_테스트() throws Exception {
        mockMvc
                .perform(get("/found/id"))
                .andExpect(status().isOk());
    }

    @Test
    public void post_ID_찾기_Email_전송() throws Exception{
        // 공백 입력
        mockMvc
                .perform(post("/found/send/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        // 존재하지 않는 Email 입력
        mockMvc
                .perform(post("/found/send/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("test@naver.com"))
                .andExpect(status().isBadRequest());

        // 성공
        mockMvc
                .perform(post("/found/send/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("ber01@naver.com"))
                .andExpect(status().isOk());
    }
}