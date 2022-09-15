package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.OrderRequestDto;
import com.backend.birdmeal.service.OrderService;
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

import java.util.List;

@RestController
@Api("OrderController")
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 내역 저장
     *
     * @param orderRequestList
     * @return Object
     */

    @ApiOperation(value="주문 내역 저장",response = Object.class)
    @PostMapping("")
    public ResponseEntity<?> setOrderInfo(@RequestBody List<OrderRequestDto> orderRequestList){
        boolean success = orderService.setOrderInfo(orderRequestList);
        ResponseFrame<?> res;

        if(success) {
            res = ResponseFrame.of(success, "주문 내역 저장을 성공했습니다.");
        }else{
            res = ResponseFrame.of(success, "상품을 판매하지 않아 주문 내역 저장을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
