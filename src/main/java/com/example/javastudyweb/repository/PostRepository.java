package com.example.javastudyweb.repository;

import com.example.javastudyweb.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Long id(Long id);
}
