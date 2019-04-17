package com.kyunghwan.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVo {

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
