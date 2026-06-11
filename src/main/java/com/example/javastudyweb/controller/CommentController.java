package com.example.javastudyweb.controller;

import com.example.javastudyweb.entity.Comment;
import com.example.javastudyweb.entity.Member;
import com.example.javastudyweb.entity.Post;
import com.example.javastudyweb.exception.CustomException;
import com.example.javastudyweb.exception.ErrorCode;
import com.example.javastudyweb.repository.MemberRepository;
import com.example.javastudyweb.repository.PostRepository;
import com.example.javastudyweb.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @PostMapping("/write")
    public String write(@RequestBody CommentRequest request){
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException((ErrorCode.MEMBER_NOT_FOUND)));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));
        commentService.write(request.getContent(),post,member);
        return "댓글 작성 성공";
    }

    @GetMapping("/read/{id}")
    public List<Comment> read(@PathVariable Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));
        return commentService.getAll(post);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestBody CommentRequest request){
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        commentService.update(id, request.getContent(), member);
        return "댓글 수정 성공!";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        commentService.delete(id, member);
        return "댓글 삭제 성공";
    }

    @Getter
    private static class CommentRequest{
        private String content;
        private Long postId;
    }
}
