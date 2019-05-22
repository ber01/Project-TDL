package com.kyunghwan.Login;

import com.kyunghwan.domain.UserDto;
import com.kyunghwan.repository.UserRepository;
import com.kyunghwan.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup(){

        // 유저 생성
        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setPwd("userPwd1!");
        userDto.setEmail("user1@ks.ac.kr");

        userService.pwdEncodingAndRegister(userDto);

        // MockMvc 초기화
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .alwaysDo(print())
                    .build();
    }

    @Test
    public void contents() throws Exception {

        // ID 오류
        mockMvc
                .perform(formLogin()
                        .user("id", "user10")
                        .password("userPwd1!"))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());

        // PWD 오류
        mockMvc
                .perform(formLogin()
                        .user("id", "user1")
                        .password("userPwd1@"))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());

        // 로그인 성공
        mockMvc
                .perform(formLogin()
                        .user("id", "user1")
                        .password("userPwd1!"))
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());

        // ToDoList 페이지 접근 실패
        mockMvc
                .perform(get("/tdl/list"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        // ToDoList 페이지 접근 성공
        UserDetails userDetails = userService.loadUserByUsername("user1");

        // redirect
        mockMvc
                .perform(get("/")
                        .with(user(userDetails)))
                .andExpect(redirectedUrl("/tdl/list"))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());

        // /tdl/list
        ResultActions resultActions = mockMvc
                .perform(get("/tdl/list")
                        .with(user(userDetails)))
                .andExpect(view().name("/tdl/list"))
                .andExpect(model().attributeExists("tdlList"))
                .andExpect(authenticated())
                .andExpect(status().isOk());

        // 세션 존재
        assertThat(resultActions.andReturn().getRequest().getSession()).isNotNull();
    }
}