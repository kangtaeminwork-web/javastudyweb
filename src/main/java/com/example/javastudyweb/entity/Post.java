package com.example.javastudyweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    private String content;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(String title, String content, Member member, String imageUrl){
        this.title = title;
        this.content = content;
        this.member = member;
        this.imageUrl = imageUrl;
    }

    public void update(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

}
