package com.kyunghwan.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Integer idx;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public void read(Comment saveComment) {
        this.setIdx(saveComment.getIdx());
        this.setContent(saveComment.getContent());
        this.setCreatedDate(saveComment.getCreatedDate());
        this.setModifiedDate(saveComment.getModifiedDate());
    }
}
