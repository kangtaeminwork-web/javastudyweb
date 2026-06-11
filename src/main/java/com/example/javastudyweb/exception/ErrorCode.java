package com.example.javastudyweb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디 입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.");

    private final HttpStatus status; // HTTP 상태코드 저장 공간
    private final String message; // 에러 메시지 저장 공간

    // 위에서 (HttpStatus.NOT_FOUND, "...") 넣으면 여기로 들어옴
    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}