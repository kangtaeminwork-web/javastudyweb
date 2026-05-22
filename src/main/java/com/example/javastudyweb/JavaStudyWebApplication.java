package com.example.javastudyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication // 이 파일이 Spring Boot의 시작점. 자동으로 주변파일 읽어줘.
public class JavaStudyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaStudyWebApplication.class, args);
    }
    // "BCryptPasswordEncoder 도구를 만들어서 Spring한테 맡겨둬, 필요한 곳에서 꺼내쓸게"
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}