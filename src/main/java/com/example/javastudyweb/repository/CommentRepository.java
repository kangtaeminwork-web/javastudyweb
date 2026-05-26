package com.example.javastudyweb.repository;

import com.example.javastudyweb.entity.Comment;
import com.example.javastudyweb.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // SELECT * FROM comment WHERE post_id = ?
    List<Comment> findAllByPost(Post post);
}

