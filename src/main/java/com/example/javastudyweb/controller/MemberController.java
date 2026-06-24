package com.example.javastudyweb.controller;

import com.example.javastudyweb.entity.Member;
import com.example.javastudyweb.exception.CustomException;
import com.example.javastudyweb.exception.ErrorCode;
import com.example.javastudyweb.jwt.JwtUtil;
import com.example.javastudyweb.repository.MemberRepository;
import com.example.javastudyweb.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/join")
    public String join(@RequestBody MemberRequest request) {
        memberService.join(request.getUsername(), request.getPassword());
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberRequest request) {
        memberService.login(request.getUsername(), request.getPassword());
        String token = jwtUtil.generateToken(request.getUsername());
        return token;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return ResponseEntity.ok(new MemberResponse(member));
    }

    @PutMapping("/password")
    public ResponseEntity<?> password(@RequestBody PasswordChangeRequest request) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        memberService.changePassword(username, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    // 클라이언트 -> 서버 방향 로그인/회원가입 데이터를 담는 그릇(입력)
    @Getter
    private static class MemberRequest {
        private String username;
        private String password;
    }

    // 서버->클라이언트 방향. 서버가 유저한테 돌려줄 데이터를 담는 그릇(출력)
    @Getter
    private static class MemberResponse {
        private final Long id;
        private final String username;
        private final String profileImageUrl;

        public MemberResponse(Member member){
            this.id = member.getId();
            this.username = member.getUsername();
            this.profileImageUrl = member.getProfileImageUrl();
        }
    }

    // 현재 비밀번호 + 새 비밀번호 DTO
    @Getter
    private static class PasswordChangeRequest {
        private String currentPassword;
        private String newPassword;
    }
}