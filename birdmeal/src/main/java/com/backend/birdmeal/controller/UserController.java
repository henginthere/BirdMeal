package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.RegistUserDto;
import com.backend.birdmeal.service.UserService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("UserController")
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    HttpStatus httpStatus;
    /**
     * 회원가입
     *
     * @param registUserDto
     * @return Object
     */
    @ApiOperation(value="회원가입",response = Object.class)
    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody RegistUserDto registUserDto){

        boolean success = userService.signup(registUserDto);
        ResponseFrame<?> res;

        if(success){
            res = ResponseFrame.of(true,"회원가입을 성공했습니다.");
        }
        else{
            res = ResponseFrame.of(false,"이미 가입된 이메일입니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
