package com.kyunghwan.service;

import com.kyunghwan.domain.Comment;
import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.repository.CommentRepository;
import com.kyunghwan.repository.ToDoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ToDoListRepository toDoListRepository;

    public void registerComment(Map<String, String> map) {
        Comment comment = new Comment();
        comment.setContent(map.get("content"));
        comment.setCreatedDate(LocalDateTime.now());
        ToDoList toDoList = toDoListRepository.findByIdx(Integer.valueOf(map.get("tdlIdx")));
        toDoList.add(comment);
        commentRepository.save(comment);
    }
}