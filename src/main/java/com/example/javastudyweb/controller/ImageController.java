package com.example.javastudyweb.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file) throws IOException {
        // 1. 파일 이름 중복 방지를 위해 UUID로 새 이름 설정
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + file.getOriginalFilename();

        // 2. 저장할 파일 경로 저장
        File saveFile = new File(uploadPath + "/" + fileName);

        // 3. 실제 파일 저장
        file.transferTo(saveFile);

        //4. 저장된 경로 반환
        return "/images/" + fileName;

    }
}
