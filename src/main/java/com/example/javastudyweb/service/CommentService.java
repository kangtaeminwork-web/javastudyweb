package com.example.javastudyweb.service;

import com.example.javastudyweb.entity.Comment;
import com.example.javastudyweb.entity.Member;
import com.example.javastudyweb.entity.Post;
import com.example.javastudyweb.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 생성
    public Comment write(String content, Post post, Member member){
        Comment comment = new Comment(content, post, member);
        return commentRepository.save(comment);
    }

    // 본인이 작성한 댓글 조회
    public List<Comment> getAll(Post post){
        return commentRepository.findAllByPost(post);
    }

    // 포스트 수정
    @Transactional
    public Long update(Long id, String content, Member member){
        // 1. 데이터 베이스에서 수정할 댓글 조회
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글 존재하지 않음"));
        // 2. 수정하려는 댓글의 작성자가 본인인지 확인
        if (!comment.getMember().getId().equals(member.getId())){
            throw new IllegalArgumentException("당신은 작성자가 아닙니다.");
        }
        // 3. 엔티티 객체 내부 매서드를 호출하여 값 변경
        comment.update(content);
        // 4. 수정된 포스트의 id 반환
        return comment.getId();
    }

    //포스트 삭제
    @Transactional
    public void delete(Long id, Member member){
        // 1. 데이터 베이스에서 삭제할 댓글 조회
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글은 이미 삭제됨"));
        // 2. 삭제하려는 댓글의 작성자가 본인인지 확인
        if (!comment.getMember().getId().equals(member.getId())){
            throw new IllegalArgumentException("당신은 작성자가 아닙니다.");
        }
        commentRepository.delete(comment);
    }



}
