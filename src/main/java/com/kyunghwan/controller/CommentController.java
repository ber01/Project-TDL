package com.kyunghwan.controller;

import com.kyunghwan.domain.CommentDto;
import com.kyunghwan.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody Map<String, String> map) {
        CommentDto commentDto = commentService.registerComment(map);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer idx){
        commentService.deleteComment(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putComment(@PathVariable("idx") Integer idx, @RequestBody String description){
        commentService.modifyComment(idx, description);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
