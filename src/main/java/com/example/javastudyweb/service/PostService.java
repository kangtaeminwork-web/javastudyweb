package com.example.javastudyweb.service;

import com.example.javastudyweb.entity.Member;
import com.example.javastudyweb.entity.Post;
import com.example.javastudyweb.exception.CustomException;
import com.example.javastudyweb.exception.ErrorCode;
import com.example.javastudyweb.repository.MemberRepository;
import com.example.javastudyweb.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 포스트 생성
    public Post write(String title, String content, Member member){
        Post post = new Post(title, content, member);
        return postRepository.save(post);
    }
    // 포스트 조회
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    // 포스트 수정
    @Transactional
    public Long update(Long id, String title, String content, Member member){

        // 1. 데이터 베이스에서 수정할 포스트 조회
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));
        // 2. 수정하려는 게시글의 작성자가 본인인지 확인
        if (!post.getMember().getId().equals(member.getId())){
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        // 3. 엔티티 객체 내부 메서드를 호출하여 값 변경
        post.update(title, content);

        // 4. 수정된 포스트의 id 반환
        return post.getId();
    }
    // 포스트 삭제
    // @Transactional 트랜잭션 끝날 때 변경된 거 자동으로 DB에 저장
    @Transactional
    public void delete(Long id, Member member){
        // 1. 데이터 베이스에서 삭제할 포스트 조회
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));
        // 2. 삭제하려는 게시글의 작성자가 본인인지 확인
        if (!post.getMember().getId().equals(member.getId())){
            throw  new CustomException(ErrorCode.UNAUTHORIZED);
        }
        postRepository.delete(post);
    }
}
