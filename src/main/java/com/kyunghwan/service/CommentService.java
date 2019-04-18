package com.kyunghwan.service;

import com.kyunghwan.domain.Comment;
import com.kyunghwan.domain.CommentDto;
import com.kyunghwan.domain.ToDoList;
import com.kyunghwan.repository.CommentRepository;
import com.kyunghwan.repository.ToDoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ToDoListRepository toDoListRepository;

    public CommentDto registerComment(Map<String, String> map) {
        Comment comment = new Comment();
        ToDoList toDoList = toDoListRepository.findByIdx(Integer.valueOf(map.get("tdlIdx")));
        comment.register(map, toDoList);
        Comment saveComment = commentRepository.save(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.read(saveComment);

        return commentDto;
    }

    public void deleteComment(Integer idx) {
        commentRepository.deleteById(idx);
    }

    public void modifyComment(Integer idx, String content) {
        Comment updateComment = commentRepository.getOne(idx);
        updateComment.update(content);
        commentRepository.save(updateComment);
    }
}
