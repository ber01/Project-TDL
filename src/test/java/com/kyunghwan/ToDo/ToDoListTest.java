package com.kyunghwan.ToDo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.domain.UserDto;
import com.kyunghwan.repository.ToDoListRepository;
import com.kyunghwan.repository.UserRepository;
import com.kyunghwan.service.ToDoListService;
import com.kyunghwan.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoListTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ToDoListService toDoListService;

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private UserDetails userDetails;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();

        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setPwd("user123!@#");
        userDto.setEmail("user1@ks.ac.kr");

        mockMvc
                .perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

        this.userDetails = userService.loadUserByUsername("user1");

        // currentUser 초기화
        mockMvc
                .perform(get("/tdl/list")
                        .with(user(userDetails)));
    }

    @Test
    public void get_요청_테스트() throws Exception{
        mockMvc
                .perform(get("/")
                            .with(user(userDetails)))
                .andExpect(redirectedUrl("/tdl/list"))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());

        mockMvc
                .perform(get("/tdl/list")
                            .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(view().name("/tdl/list"))
                .andExpect(model().attributeExists("tdlList"))
                .andExpect(status().isOk());
    }

    @Test
    public void To_Do_등록_실패_내용길이() throws Exception{
        // Description 길이 0
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("");

        mockMvc
                .perform(post("/tdl")
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isBadRequest());

        // Description 길이 35 이상
        toDoList = new ToDoList();
        toDoList.setDescription("가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마");

        mockMvc
                .perform(post("/tdl")
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void To_Do_등록_성공_테스트() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("Description 테스트");

        // ToDo 등록 post
        mockMvc
                .perform(post("/tdl")
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

        // 내용, 상태, 생성, 완료 날짜, User_idx 확인
        toDoList = toDoListRepository.findAll().get(0);
        assertThat(toDoList).isNotNull();
        assertThat(toDoList.getDescription()).isEqualTo("Description 테스트");
        assertThat(toDoList.getStatus()).isFalse();
        assertThat(toDoList.getCreatedDate()).isNotNull();
        assertThat(toDoList.getCompletedDate()).isNull();
        assertThat(toDoList.getUser().getIdx()).isEqualTo(userService.findCurrentUser(userDetails.getUsername()).getIdx());
    }

    @Test
    public void To_Do_완료_테스트() throws Exception {
        for (int i = 1; i <= 5; i++){
            ToDoList toDoList = new ToDoList();
            toDoList.setDescription("Description 테스트" + i);

                mockMvc
                        .perform(post("/tdl")
                                .with(user(userDetails))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(toDoList)))
                        .andExpect(status().isCreated());
        }

        // 초기 완료 상태 F
        assertThat(toDoListRepository.findByIdx(2).getStatus()).isFalse();
        // 완료 시간 null
        assertThat(toDoListRepository.findByIdx(2).getCompletedDate()).isNull();

        // 완료 put 요청
        mockMvc
                .perform(put("/tdl/complete/2")
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 수행 후 완료 상태 T
        assertThat(toDoListRepository.findByIdx(2).getStatus()).isTrue();
        // 완료 시간 not null
        assertThat(toDoListRepository.findByIdx(2).getCompletedDate()).isNotNull();

        // 이외의 ToDo 상태 F
        assertThat(toDoListRepository.findByIdx(1).getStatus()).isFalse();
        assertThat(toDoListRepository.findByIdx(3).getStatus()).isFalse();
        assertThat(toDoListRepository.findByIdx(4).getStatus()).isFalse();
        assertThat(toDoListRepository.findByIdx(5).getStatus()).isFalse();
    }

    @Test
    public void To_Do_삭제_테스트() throws Exception {
        for (int i = 1; i <= 3; i++){
            ToDoList toDoList = new ToDoList();
            toDoList.setDescription("Description 테스트" + i);

            mockMvc
                    .perform(post("/tdl")
                            .with(user(userDetails))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(toDoList)))
                    .andExpect(status().isCreated());
        }

        // 삭제 전 ToDo 객체 확인
        IntStream.rangeClosed(1, 5).forEach(System.out::println);
        assertThat(toDoListRepository.findByIdx(1)).isNotNull();
        assertThat(toDoListRepository.findByIdx(2)).isNotNull();
        assertThat(toDoListRepository.findByIdx(3)).isNotNull();

        /*
        // 삭제
        mockMvc
                .perform(delete())
                */
    }
}
