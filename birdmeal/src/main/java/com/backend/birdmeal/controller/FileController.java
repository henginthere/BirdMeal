package com.backend.birdmeal.controller;

import com.backend.birdmeal.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final AwsS3Service awsS3Service;

    @PostMapping
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return awsS3Service.upload(multipartFile, "seller");
    }

}
