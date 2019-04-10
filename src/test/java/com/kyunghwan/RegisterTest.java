package com.kyunghwan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyunghwan.domain.User;
import com.kyunghwan.domain.UserDto;
import com.kyunghwan.repository.UserRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void A_로그인_페이지_GET() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void B_회원가입_페이지_GET() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void C_회원가입_POST() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setEmail("ksyj8256@ks.ac.kr");
        userDto.setPwd("!test123");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

        User user = userRepository.findById("user1");
        assertThat("user1", is(user.getId()));
        assertThat("ksyj8256@ks.ac.kr", is(user.getEmail()));
    }

    @Test
    public void D_회원가입_ID_오류() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("a");
        userDto.setEmail("ksyj8256@ks.ac.kr");
        userDto.setPwd("!test123");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void E_회원가입_EMAIL_오류() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setEmail("ksyj8256");
        userDto.setPwd("!test123");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void F_회원가입_PWD_오류() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setEmail("ksyj8256@ks.ac.kr");
        userDto.setPwd("test123");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }
}
