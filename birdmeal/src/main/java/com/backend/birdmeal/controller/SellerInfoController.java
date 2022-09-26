package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.*;
import com.backend.birdmeal.repository.SellerInfoRepository;
import com.backend.birdmeal.service.SellerInfoService;
import com.backend.birdmeal.util.ResponseFrame;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@Api("SellerInfoController")
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerInfoController {

    private final SellerInfoService sellerInfoService;
    private final SellerInfoRepository sellerInfoRepository;

    /**
     * 회원가입
     *
     * @param registSellerDto
     * @return Object
     */
    @ApiOperation(value="회원가입",response = Object.class)
    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody RegistSellerDto registSellerDto){

        boolean success = sellerInfoService.signup(registSellerDto);
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
    public ResponseEntity<?> login(@RequestBody GoogleLoginDto googleAccessToken) throws IOException, GeneralSecurityException {
        ResponseFrame<?> res;
        String sellerEmail;
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, googleAccessToken.getGoogleAccessToken());


        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(new NetHttpTransport(),
                new JacksonFactory());
        // 토큰 유효성 확인
        if (verifier.verify(idToken)) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            sellerEmail = payload.getEmail();
        }
        else {
            //Invalid ID token
            res = ResponseFrame.of(false,"로그인에 실패하였습니다.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        //t_seller에 email 존재 여부 확인
        if(sellerInfoRepository.findBySellerEmail(sellerEmail)!=null){

            ResponseSellerLoginDto responseSellerLoginDto = sellerInfoService.login(sellerEmail);

            if(responseSellerLoginDto!=null){
                res = ResponseFrame.of(responseSellerLoginDto,"로그인에 성공하였습니다.");
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
     * 판매자 정보 요청
     *
     * @param sellerSeq
     * @return Object
     */

    @ApiOperation(value="판매자 정보 요청",response = Object.class)
    @GetMapping("/{seller-seq}")
    public ResponseEntity<?> getSellerInfo(@PathVariable("seller-seq") long sellerSeq){
        SellerDto sellerDto = sellerInfoService.getSellerInfo(sellerSeq);
        ResponseFrame<?> res;

        if(sellerDto == null){
            res = ResponseFrame.of(false, "판매자가 없어서 판매자 정보 요청을 실패했습니다.");
        }else {
            res = ResponseFrame.of(sellerDto, "판매자 정보 요청을 성공했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    /**
     * 판매자 정보 수정
     *
     * @param sellerUpdateDto
     * @return Object
     */

    @ApiOperation(value="판매자 정보 수정",response = Object.class)
    @PutMapping("")
    public ResponseEntity<?> updateSellerInfo(@RequestBody SellerUpdateDto sellerUpdateDto){
        boolean success = sellerInfoService.updateSellerInfo(sellerUpdateDto);
        ResponseFrame<?> res;
        if(success) {
            res = ResponseFrame.of(success, "판매자 정보 수정을 성공했습니다.");
        }else{
            res = ResponseFrame.of(success, "판매자가 존재하지 않아 판매자 정보 수정을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 판매자 정보 유무 확인
     *
     * @param sellerSeq
     * @return Object
     */

    @ApiOperation(value="판매자 정보 유무 확인",response = Object.class)
    @GetMapping("/info/{seller-seq}")
    public ResponseEntity<?> checkSellerInfo(@PathVariable("seller-seq") long sellerSeq){
        ResponseFrame<?> res;

        // 만약 판매자가 존재하지 않으면
        if(sellerInfoRepository.findBySellerSeq(sellerSeq) == null){
            boolean f = false;
            res = ResponseFrame.of(f, "판매자가 존재하지 않습니다.");
        }
        else {
            boolean success = sellerInfoService.checkSellerInfo(sellerSeq);
            if (success) {
                res = ResponseFrame.of(success, "판매자 정보가 있습니다.");
            } else {
                res = ResponseFrame.of(success, "판매자 정보가 없습니다.");
            }
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     * 판매자 지갑 저장
     *
     * @param sellerWalletDto
     * @return Object
     */

    @ApiOperation(value="판매자 지갑 저장",response = Object.class)
    @PutMapping("/wallet")
    public ResponseEntity<?> setSellerWallet(@RequestBody SellerWalletDto sellerWalletDto){
        boolean success = sellerInfoService.setSellerWallet(sellerWalletDto);
        ResponseFrame<?> res;
        if(success) {
            res = ResponseFrame.of(success, "판매자 지갑 등록을 성공했습니다.");
        }else{
            res = ResponseFrame.of(success, "판매자가 존재하지 않아 판매자 지갑 등록을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
