package com.kyunghwan.Comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.domain.UserDto;
import com.kyunghwan.repository.CommentRepository;
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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDetails userDetails;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setup() throws Exception {
        // MockMvc 초기화
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .alwaysDo(print())
                    .build();

        // User 객체 생성
        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setEmail("user1@ks.ac.kr");
        userDto.setPwd("user123!@#");

        // User POST 요청
        mockMvc
                .perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

        // currentUser 초기화
        this.userDetails = userService.loadUserByUsername("user1");

        // 현재 유저 초기화
        mockMvc
                .perform(get("/tdl/list").with(user(userDetails)));

        // tdl 객체 3개 생성 및 POST 요청
        createTdlWithRequestPost("첫 번째 ToDo");
        createTdlWithRequestPost("두 번째 ToDo");
        createTdlWithRequestPost("세 번째 ToDo");

    }

    // tdl 객체 생성 및 POST 요청 메소드
    private void createTdlWithRequestPost(String description) throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription(description);

        mockMvc
                .perform(post("/tdl")
                        .with(user(this.userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());
    }

    @Test
    public void 댓글_POST_테스트() throws Exception {
        createCommentWithRequestPost("첫 번째 ToDo에 등록되는 첫 번째 댓글", "1");
        createCommentWithRequestPost("두 번째 ToDo에 등록되는 첫 번째 댓글", "2");
        createCommentWithRequestPost("첫 번째 ToDo에 등록되는 두 번째 댓글", "1");
        createCommentWithRequestPost("세 번째 ToDo에 등록되는 첫 번째 댓글", "3");
        createCommentWithRequestPost("세 번째 ToDo에 등록되는 두 번째 댓글", "3");
        createCommentWithRequestPost("첫 번째 ToDo에 등록되는 세 번째 댓글", "1");
        assertThat(commentRepository.findAll()).hasSize(6);

        // tdl, comment 관계성 확인
        assertThat(commentRepository.findByToDoListIdx(1)).hasSize(3);
        assertThat(commentRepository.findByToDoListIdx(2)).hasSize(1);
        assertThat(commentRepository.findByToDoListIdx(3)).hasSize(2);
    }

    // comment 생성을 위한 요청 map 생성 및 POST 요청 메소드
    private void createCommentWithRequestPost(String content, String idx) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("tdlIdx", idx);

        mockMvc
                .perform(post("/comment")
                        .with(user(this.userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated());
    }

    @Test
    public void 댓글_PUT_테스트() throws Exception{
        // 댓글 등록
        createCommentWithRequestPost("ToDo 댓글 달기", "1");

        // 수정 전 수정 시간 = null
        assertThat(commentRepository.findByIdx(1).getModifiedDate()).isNull();
        assertThat(commentRepository.findByIdx(1).getContent()).isEqualTo("ToDo 댓글 달기");

        // 수정 요청
        mockMvc
                .perform(put("/comment/1")
                        .with(user(this.userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("ToDo 댓글 수정"))
                .andExpect(status().isOk());

        // 수정 후 시간 != null
        assertThat(commentRepository.findByIdx(1).getModifiedDate()).isNotNull();
        assertThat(commentRepository.findByIdx(1).getContent()).isEqualTo("ToDo 댓글 수정");
    }

    @Test
    public void 댓글_DELETE_테스트() throws Exception {
        // 댓글 등록
        createCommentWithRequestPost("1번 댓글", "1");
        createCommentWithRequestPost("2번 댓글", "1");
        createCommentWithRequestPost("3번 댓글", "1");

        // 삭제 전 != null
        assertThat(commentRepository.findByIdx(1)).isNotNull();
        assertThat(commentRepository.findByIdx(2)).isNotNull();
        assertThat(commentRepository.findByIdx(3)).isNotNull();

        // 2번 댓글 삭제
        mockMvc
                .perform(delete("/comment/2")
                        .with(user(this.userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 삭제 후 2번 null
        assertThat(commentRepository.findByIdx(1)).isNotNull();
        assertThat(commentRepository.findByIdx(2)).isNull();
        assertThat(commentRepository.findByIdx(3)).isNotNull();
    }
}
