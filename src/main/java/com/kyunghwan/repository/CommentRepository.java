package com.kyunghwan.repository;

import com.kyunghwan.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findByIdx(int idx);
    List<Comment> findByToDoListIdx(int idx);
}
