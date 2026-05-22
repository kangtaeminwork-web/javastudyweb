package com.example.javastudyweb.controller;

import com.example.javastudyweb.jwt.JwtUtil;
import com.example.javastudyweb.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
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
    public String me(@RequestHeader("Authorization") String token) {
        return "현재 로그인한 사용자: " + token;
    }

    @Getter
    private static class MemberRequest {
        private String username;
        private String password;
    }
}