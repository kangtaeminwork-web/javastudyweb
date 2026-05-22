package com.example.javastudyweb.controller;

import com.example.javastudyweb.entity.Member;
import com.example.javastudyweb.entity.Post;
import com.example.javastudyweb.repository.MemberRepository;
import com.example.javastudyweb.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;

    @PostMapping("/write")
    public String write(@RequestBody PostRequest request){
        // JwtFilter에서 토큰 검증 후 저장한 username 꺼내기
        String username = SecurityContextHolder.getContext()
                        .getAuthentication().getName();
        // username으로 Member객체를 DB에서 찾아오는 코드
        Member member = memberRepository.findByUsername(username)
                        .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저 입니다."));
        postService.write(request.getTitle(), request.getContent(), member);
        return "글 작성 성공";
    }

    @GetMapping("/read")
    public List<Post> read(){
            return postService.getAll();
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestBody PostRequest request){
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저 입니다."));
        postService.update(id ,request.getTitle(), request.getContent(), member);
        return "글 수정 성공!";
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저 입니다."));
        postService.delete(id, member);
        return "삭제 성공";
    }


    @Getter
    private static class PostRequest {
        private String title;
        private String content;
    }
}
