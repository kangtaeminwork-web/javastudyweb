package com.example.javastudyweb.service;

import com.example.javastudyweb.entity.Member;
import com.example.javastudyweb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //final 붙은 필드를 자동으로 생성자 주입(Lombok)
public class MemberService {

    private final MemberRepository memberRepository;
    // 비밀번호를 암호화하는 도구. 같은 비밀번호도 매번 다르게 암호화됨
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    public Member join(String username, String password) {
        // 중복 아이디 체크
        memberRepository.findByUsername(username).ifPresent(m -> {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        });

        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member(username, encodedPassword);
        return memberRepository.save(member);
    }

    // 로그인
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return member;
    }
}