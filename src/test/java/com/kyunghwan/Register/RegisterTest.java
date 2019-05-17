package com.kyunghwan.Register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyunghwan.domain.UserDto;
import com.kyunghwan.repository.UserRepository;
import com.kyunghwan.service.RegisterService;
import com.kyunghwan.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RegisterService registerService;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    public void registerTest() throws Exception {

        // GET 요청, 200
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/register"));

        // 없는 URL GET 요청, 302
        mockMvc.perform(get("/unknown"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        // ID 공백
        UserDto userDto = new UserDto();
        userDto.setId("");
        userDto.setEmail("user@ks.ac.kr");
        userDto.setPwd("test123!");
        valid(userDto, 400, 0);

        // ID 길이 부적합(5 미만)
        userDto.setId("use1");
        valid(userDto, 400, 0);

        // ID 길이 부적합(10 초과)
        userDto.setId("user1user2user3");
        valid(userDto, 400, 0);

        // ID 부적합(특수문자 사용)
        userDto.setId("user1!");
        valid(userDto, 400, 0);

        // ID 부적합(대문자 사용)
        userDto.setId("User1");
        valid(userDto, 400, 0);

        // ID 적합
        userDto.setId("user1");
        valid(userDto, 201, 1);

        // E-mail 공백
        UserDto userDto2 = new UserDto();
        userDto2.setId("user2");
        userDto2.setEmail("");
        userDto2.setPwd("test123!");
        valid(userDto2, 400, 1);

        // E-mail 형식 오류
        userDto2.setEmail("user2");
        valid(userDto2, 400, 1);

        // E-mail 적합
        userDto2.setEmail("user2@ks.ac.kr");
        valid(userDto2, 201, 2);

        // PWD 공백
        UserDto userDto3 = new UserDto();
        userDto3.setId("user3");
        userDto3.setEmail("user3@ks.ac.kr");
        userDto3.setPwd("");
        valid(userDto3, 400, 2);

        // PWD 길이 부적합(8 미만)
        userDto3.setPwd("!Qwe3");
        valid(userDto3, 400, 2);

        // PWD 길이 부적합(16 초과)
        userDto3.setPwd("!Qwe3qwer12345678qr21r3!");
        valid(userDto3, 400, 2);

        // PWD 영문 제외
        userDto3.setPwd("12345!@#$%");
        valid(userDto3, 400, 2);

        // PWD 숫자 제외
        userDto3.setPwd("qwert!@#$%");
        valid(userDto3, 400, 2);

        // PWD 특수문자 제외
        userDto3.setPwd("qwert12345");
        valid(userDto3, 400, 2);

        // PWD 적합
        userDto3.setPwd("!user12345");
        valid(userDto3, 201, 3);

        // 등록 ID, E-mail, 데이터베이스 ID, E-mail 일치 확인 - 1
        registerEqualDb(1, userDto);

        // 등록 ID, E-mail, 데이터베이스 ID, E-mail 일치 확인 - 2
        registerEqualDb(2, userDto2);

        // 등록 ID, E-mail, 데이터베이스 ID, E-mail 일치 확인 - 3
        registerEqualDb(3, userDto3);

        // ID 중복 검사(user1)
        duplication("id", "user1", 400);

        // ID 중복 검사(user2)
        duplication("id", "user2", 400);

        // ID 중복 검사(user3)
        duplication("id", "user3", 400);

        // ID 중복 X
        duplication("id", "user4", 200);

        // E-mail 중복 검사(user@ks.ac.kr)
        duplication("email", "user@ks.ac.kr", 400);

        // E-mail 중복 검사(user2@ks.ac.kr)
        duplication("email", "user2@ks.ac.kr", 400);

        // E-mail 중복 검사(user3@ks.ac.kr)
        duplication("email", "user3@ks.ac.kr", 400);

        // E-mail 중복 X
        duplication("email", "user4@ks.ac.kr", 200);
    }

    private void registerEqualDb(int idx, UserDto userDto) {
        assertThat(this.userRepository.findByIdx(idx).getId()).isEqualTo(userDto.getId());
        assertThat(this.userRepository.findByIdx(idx).getEmail()).isEqualTo(userDto.getEmail());
    }

    private void duplication(String type, String args, int http) throws Exception {

        ResultActions ra = this.mockMvc.perform(post("/register/duplication/" + type)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(args));
        if (http == 400){
            ra.andExpect(status().isBadRequest());
        } else if (http == 200){
            ra.andExpect(status().isOk());
        }
    }

    public void valid(UserDto userDto, int http, int n) throws Exception {

        ResultActions ra = this.mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userDto)));
        if (http == 400) {
            ra.andExpect(status().isBadRequest());
        } else if (http == 201){
            ra.andExpect(status().isCreated());
        }
        assertThat(userRepository.findAll()).hasSize(n);
    }
}
