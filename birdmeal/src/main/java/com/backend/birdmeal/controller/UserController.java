package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.*;
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
import java.util.logging.Logger;


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

        ResponseLoginDto responseLoginDto = userService.signup(registUserDto);
        ResponseFrame<?> res;

        if(responseLoginDto!=null){
            res = ResponseFrame.of(responseLoginDto,"회원가입을 성공했습니다.");
        }
        else{
            res = ResponseFrame.of(false,"이미 가입된 이메일입니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 로그인
     *
     * @param googleLoginDto
     * @return Object
     */
    @ApiOperation(value="로그인",response = Object.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody GoogleLoginDto googleLoginDto) throws IOException, GeneralSecurityException {
        ResponseFrame<?> res;
        String userEmail;
        String googleAccessToken = googleLoginDto.getGoogleAccessToken();
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
        if(userRepository.findByUserEmail(userEmail).isPresent()){
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
        else {
            res = ResponseFrame.of(false,"로그인에 실패하였습니다.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

    }

    /**
     * 회원 정보 조회
     *
     * @param userSeq
     * @return Object
     */
    @ApiOperation(value="회원 정보 조회",response = Object.class)
    @GetMapping("/{user-seq}/info")
    public ResponseEntity<?> getUserInfo(@PathVariable("user-seq") long userSeq){

        UserDto userDto = userService.getUserInfo(userSeq);
        ResponseFrame<?> res;

        if(userDto!=null){
            res = ResponseFrame.of(userDto,"회원 정보 조회를 성공했습니다.");
        }
        else{
            res = ResponseFrame.of(false,"회원 정보 조회를 실패했습니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    /**
     * 회원 정보 수정
     *
     * @param updateUserDto
     * @return Object
     */
    @ApiOperation(value="회원 정보 수정",response = Object.class)
    @PutMapping("/{user-seq}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable("user-seq") long userSeq){
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

    /**
     * 결식 아동 확인
     *
     * @param starvingChildDto
     * @return Object
     */
    @ApiOperation(value="결식 아동 확인",response = Object.class)
    @PostMapping("/check-child")
    public ResponseEntity<?> checkChild(@RequestBody StarvingChildDto starvingChildDto){
        boolean success = userService.checkChild(starvingChildDto);
        ResponseFrame<?> res;

        if(success){
            res = ResponseFrame.of(true,"결식 아동입니다.");
        }
        else{
            res = ResponseFrame.of(false,"결식 아동이 아닙니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    /**
     * 지갑 eoa 저장
     *
     * @param saveWalletDto
     * @return Object
     */
    @ApiOperation(value="지갑 eoa 저장",response = Object.class)
    @PutMapping("/wallet")
    public ResponseEntity<?> saveWallet(@RequestBody SaveWalletDto saveWalletDto){
        boolean success = userService.saveWallet(saveWalletDto.getUserSeq(), saveWalletDto.getUserEoa());
        ResponseFrame<?> res;

        if(success){
            res = ResponseFrame.of(true,"지갑 eoa 저장을 완료했습니다.");
        }
        else{
            res = ResponseFrame.of(false,"지갑 eoa 저장을 실패했습니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    /**
     * 결식 아동 충전 상태 변경
     *
     * @param userSeq
     * @return Object
     */
    @ApiOperation(value="결식 아동 충전 상태 변경",response = Object.class)
    @PutMapping("/check-child/{user-seq}")
    public ResponseEntity<?> updateChildState(@PathVariable("user-seq") long userSeq){
        boolean success = userService.updateChildState(userSeq);
        ResponseFrame<?> res;

        if(success){
            res = ResponseFrame.of(true,"결식 아동 충전 상태를 변경했습니다.");
        }
        else{
            res = ResponseFrame.of(false,"회원이 없어 결식 아동 충전 상태 변경을 못했습니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
