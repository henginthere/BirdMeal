package com.backend.birdmeal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final AwsS3Service awsS3Service;

    public String fileUpload(MultipartFile req) throws IOException {
        String url = awsS3Service.upload(req);
        return url;
    }
}
