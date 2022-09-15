package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.RegistUserDto;
import com.backend.birdmeal.dto.ResponseLoginDto;
import com.backend.birdmeal.dto.UpdateUserDto;
import com.backend.birdmeal.repository.UserRepository;
import com.backend.birdmeal.service.UserService;
import com.backend.birdmeal.util.ResponseFrame;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;


@RestController
@Api("UserController")
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

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

    /**
     * 로그인
     *
     * @param googleAccessToken
     * @return Object
     */
    @ApiOperation(value="로그인",response = Object.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String googleAccessToken) throws IOException, GeneralSecurityException {
        ResponseFrame<?> res;
        String userEmail;
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, googleAccessToken);


        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(new NetHttpTransport(),
                new JacksonFactory());
        if (verifier.verify(idToken)) {
            Payload payload = idToken.getPayload();
            userEmail = payload.getEmail();
            System.out.println("User Email: "+userEmail);
//            String userNickname = (String)payload.get("name");
//            System.out.println("User Name: "+userNickname);
        }
        else {
            //Invalid ID token
            System.out.println("만료된 토큰");
            res = ResponseFrame.of(false,"로그인에 실패하였습니다.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        //t_user에 email 존재 여부 확인
        if(userRepository.findByUserEmail(userEmail)!=null){

            ResponseLoginDto responseLoginDto = userService.login(userEmail);
            if(responseLoginDto!=null){
                res = ResponseFrame.of(responseLoginDto,"로그인에 성공하였습니다.");
                return new ResponseEntity<>(res,HttpStatus.OK);
            }
            else{
                res = ResponseFrame.of(false,"로그인에 실패하였습니다.");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }

        }

        res = ResponseFrame.of(false,"로그인에 실패하였습니다.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 회원 정보 수정
     *
     * @param updateUserDto
     * @return Object
     */
    @ApiOperation(value="회원 정보 수정",response = Object.class)
    @PutMapping("/{userSeq}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable Long userSeq){
        boolean success = userService.updateUser(userSeq, updateUserDto);
        ResponseFrame<?> res;

        if(success){
            res = ResponseFrame.of(true,"회원 정보 수정을 성공했습니다.");
        }
        else{
            res = ResponseFrame.of(false,"회원 정보 수정을 실패했습니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
