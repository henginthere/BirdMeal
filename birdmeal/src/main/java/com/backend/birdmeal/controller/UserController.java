package com.backend.birdmeal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("UserController")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value="회원가입",response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(){
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
