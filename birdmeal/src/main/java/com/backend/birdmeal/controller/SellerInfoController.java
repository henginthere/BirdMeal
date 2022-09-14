package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.SellerDto;
import com.backend.birdmeal.service.SellerInfoService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("SellerInfoController")
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerInfoController {

    private final SellerInfoService sellerInfoService;

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

        ResponseFrame<?> res = ResponseFrame.of(sellerDto,"판매자 정보 요청을 성공했습니다.");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     * 판매자 정보 등록
     *
     * @param sellerDto
     * @return Object
     */

    @ApiOperation(value="판매자 정보 등록",response = Object.class)
    @PostMapping("")
    public ResponseEntity<?> setSellerInfo(@RequestBody SellerDto sellerDto){
        boolean success = sellerInfoService.setSellerInfo(sellerDto);

        ResponseFrame<?> res = ResponseFrame.of(success,"판매자 정보 등록을 성공했습니다.");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }



}
