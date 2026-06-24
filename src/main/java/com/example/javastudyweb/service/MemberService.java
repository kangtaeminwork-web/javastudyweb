package com.example.javastudyweb.service;

import com.example.javastudyweb.entity.Member;
import com.example.javastudyweb.exception.CustomException;
import com.example.javastudyweb.exception.ErrorCode;
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
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        });

        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member(username, encodedPassword);
        return memberRepository.save(member);
    }

    // 로그인
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return member;
    }

    // 비밀번호 변경 로직
    public void changePassword(String username , String currentPassword, String newPassword) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        member.updatePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }
}