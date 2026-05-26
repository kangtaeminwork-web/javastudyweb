package com.example.javastudyweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String content;

    @Column(nullable = false, unique = false)
    @CreationTimestamp // 자동으로 현재 시간 저장하는 어노테이션
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(String content, Post post, Member member){
        this.content = content;
        this.post = post;
        this.member = member;
    }
    public void update(String content){
        this.content = content;
    }

}
