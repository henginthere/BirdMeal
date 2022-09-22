package com.backend.birdmeal.controller;

import com.backend.birdmeal.service.FileService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/file")
@Api("FileController")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 업로드
     *
     * @param req
     * @return Object
     */

    @ApiOperation(value="파일 업로드",response = Object.class)
    @PostMapping("")
    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile req) throws IOException {
        String url = fileService.fileUpload(req);
        ResponseFrame<?> res;


        if(url.length() != 0) {
            res = ResponseFrame.of(url, "파일 업로드를 성공했습니다.");
        }else{
            res = ResponseFrame.of(url, "파일 업로드에 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * HTMl 페이지 반환
     *
     * @param
     * @return Object
     */

    @ApiOperation(value="HTMl 페이지 반환",response = Object.class)
    @GetMapping("/html")
    public String htmlFile(){
        return "address";
    }

}
