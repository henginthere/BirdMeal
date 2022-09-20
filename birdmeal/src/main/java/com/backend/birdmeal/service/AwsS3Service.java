package com.backend.birdmeal.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AwsS3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String sellerName, String productName) throws IOException {
        System.out.println("파일 : " + multipartFile);
        System.out.println("멀티파트파일 : " + multipartFile.getOriginalFilename());
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, sellerName, productName);
    }

    private String upload(File uploadFile, String sellerName, String productName) {
        String subFileName = buildFileName("image", uploadFile.getName());
        String fileName = sellerName + "/" + productName + "_" + subFileName;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("파일이 삭제되었습니다.");
        } else {
            System.out.println("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        System.out.println("파일이름확인 : " + file.getOriginalFilename());
        File convertFile = new File(file.getOriginalFilename());
//        System.out.println(file.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
//        System.out.println(convertFile.getAbsolutePath());
        return Optional.of(convertFile);

    }

    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String CATEGORY_PREFIX = ".";
    private static final String TIME_SEPARATOR = "_";

    public static String buildFileName(String category, String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return category + CATEGORY_PREFIX + fileName + TIME_SEPARATOR + now + fileExtension;
    }

}
