package com.kyunghwan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyunghwan.domain.PasswordDto;
import com.kyunghwan.domain.UserDto;
import com.kyunghwan.service.FoundService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    FoundService foundService;

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
    public void post_ID_찾기_Email_전송() throws Exception {
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

    @Test
    public void get_PWD_찾기_요청_테스트() throws Exception {
        mockMvc
                .perform(get("/found/pwd"))
                .andExpect(status().isOk());
    }

    @Test
    public void post_PWD_찾기_ID_Email_전송_실패_테스트() throws Exception {
        // 아이디 공백
        String data = "{\"id\":\"\",\"email\":\"ber01@naver.com\"}";
        mockMvc
                .perform(post("/found/send/id-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isBadRequest());

        // 이메일 공백
        data = "{\"id\":\"ber01\",\"email\":\"\"}";
        mockMvc
                .perform(post("/found/send/id-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isBadRequest());

        // 존재하지 않는 아이디
        data = "{\"id\":\"user1\",\"email\":\"ber01@naver.com\"}";
        mockMvc
                .perform(post("/found/send/id-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isBadRequest());

        // 존재하지 않는 이메일
        data = "{\"id\":\"ber01\",\"email\":\"user01@naver.com\"}";
        mockMvc
                .perform(post("/found/send/id-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isBadRequest());

        // 아이디, 이메일 둘 다 불일치
        data = "{\"id\":\"user1\",\"email\":\"user01@naver.com\"}";
        mockMvc
                .perform(post("/found/send/id-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_PWD_찾기_ID_Email_전송_성공_테스트() throws Exception {
        String data = "{\"id\":\"ber01\",\"email\":\"ber01@naver.com\"}";

        // 존재하는 ID, 비밀번호 전송
        mockMvc
                .perform(post("/found/send/id-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isOk());
    }

    @Test
    public void get_인증번호_화면_요청_테스트() throws Exception{
        mockMvc
                .perform(get("/found/number"))
                .andExpect(status().isOk());
    }

    @Test
    public void get_PWD_초기화_화면_요청_테스트() throws Exception {
        mockMvc
                .perform(get("/found/reset"))
                .andExpect(status().isOk());
    }

    @Test
    public void post_PWD_변경_테스트() throws Exception {

        /*
        // 비밀번호 공백
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setPwd("");
        mockMvc
                .perform(post("/found/send/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isBadRequest());

        // 비밀번호 제약 위반
        passwordDto.setPwd("test123456");
        mockMvc
                .perform(post("/found/send/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isBadRequest());

        // 비밀번호 길이 8 미만
        passwordDto.setPwd("test1!");
        mockMvc
                .perform(post("/found/send/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isBadRequest());

        // 비밀번호 길이 16 초과
        passwordDto.setPwd("test11231231231923789123789123789127389!");
        mockMvc
                .perform(post("/found/send/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isBadRequest());
        */
    }
}