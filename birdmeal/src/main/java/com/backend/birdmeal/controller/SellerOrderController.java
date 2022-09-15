package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.OrderDeatilInfoDto;
import com.backend.birdmeal.dto.SellerDto;
import com.backend.birdmeal.dto.SellerOrderResponseDto;
import com.backend.birdmeal.service.SellerInfoService;
import com.backend.birdmeal.service.SellerOrderService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("SellerOrderController")
@RequiredArgsConstructor
@RequestMapping("/api/seller/order")
public class SellerOrderController {

    private final SellerOrderService sellerOrderService;
    private final SellerInfoService sellerInfoService;

    /**
     * 구매 내역 목록 보기
     *
     * @param sellerSeq
     * @return Object
     */

//    @ApiOperation(value="구매 내역 목록 보기",response = Object.class)
//    @GetMapping("/{seller-seq}")
//    public ResponseEntity<?> getSellerOrderProduct(@PathVariable("seller-seq") long sellerSeq){
//        List<SellerOrderResponseDto> sellerOrderResponseDtoList = sellerOrderService.getSellerOrderProduct(sellerSeq);
//        ResponseFrame<?> res;
//
//        // 판매자 정보
//        SellerDto sellerDto = sellerInfoService.getSellerInfo(sellerSeq);
//
//
//        if(sellerDto == null){
//            res = ResponseFrame.of(false,"판매자의 정보가 존재하지 않아 구매 내역 목록 보기을 실패했습니다.");
//        }else{
//            res = ResponseFrame.of(sellerOrderResponseDtoList,"구매 내역 목록 보기을 성공했습니다.");
//        }
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }


    /**
     * 배송 정보 입력
     *
     * @param orderDeatilInfoDto
     * @return Object
     */

    @ApiOperation(value="배송 정보 입력",response = Object.class)
    @PutMapping("")
    public ResponseEntity<?> updateSellerOrderInfo(@RequestBody OrderDeatilInfoDto orderDeatilInfoDto){
        boolean success = sellerOrderService.updateSellerOrderInfo(orderDeatilInfoDto);
        ResponseFrame<?> res;

        if(success){
            res = ResponseFrame.of(success,"배송 정보 입력을 성공했습니다.");
        }else{
            res = ResponseFrame.of(false,"주문이 없으므로 배송 정보 입력을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
