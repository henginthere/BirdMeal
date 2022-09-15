package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.service.SellerProductService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("SellerProductController")
@RequiredArgsConstructor
@RequestMapping("/api/seller/product")
public class SellerProductController {

    private final SellerProductService sellerProductService;

    /**
     * 상품 판매 등록
     *
     * @param productDto
     * @return Object
     */

    @ApiOperation(value="상품 판매 등록",response = Object.class)
    @PostMapping("")
    public ResponseEntity<?> setSellerProduct(@RequestBody ProductDto productDto){
        ResponseFrame<?> res = ResponseFrame.of(sellerProductService.setSellerProduct(productDto),"상품 판매 등록을 성공했습니다.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
