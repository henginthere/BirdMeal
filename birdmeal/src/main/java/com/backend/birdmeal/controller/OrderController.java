package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.*;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.OrderRepository;
import com.backend.birdmeal.repository.UserRepository;
import com.backend.birdmeal.service.OrderService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api("OrderController")
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    // 나중에 Service로 대체 ( 회원정보 불러오기로 )
    private final UserRepository userRepository; 

    private final OrderRepository orderRepository;

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

    /**
     * 내 주문 불러오기
     *
     * @param userSeq
     * @return Object
     */

    @ApiOperation(value="내 주문 불러오기",response = Object.class)
    @GetMapping("/list/{user-seq}")
    public ResponseEntity<?> getMyOrderInfo(@PathVariable("user-seq") long userSeq){
        List<MyOrderResponseDto> list = orderService.getMyOrderInfo(userSeq);
        ResponseFrame<?> res;

        // 회원정보 확인
        Optional<UserEntity> userEntity = userRepository.findByUserSeq(userSeq);
        
        if(userEntity.isPresent()) {
            res = ResponseFrame.of(list, "내 주문 불러오기을 성공했습니다.");
        }else{
            res = ResponseFrame.of(false, "사용자가 없어 내 주문 불러오기을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 내 주문 상세 내역 불러오기
     *
     * @param userSeq
     * @return Object
     */

    @ApiOperation(value="내 주문 상세 내역 불러오기",response = Object.class)
    @GetMapping("/list/{user-seq}/{order-seq}")
    public ResponseEntity<?> getMyOrderInfo(@PathVariable("user-seq") long userSeq, @PathVariable("order-seq") long orderSeq){
        List<MyOrderDetailResponseDto> list = orderService.getMyOrderDetailInfo(userSeq,orderSeq);
        ResponseFrame<?> res;

        // 회원정보 확인
        Optional<UserEntity> userEntity = userRepository.findByUserSeq(userSeq);

        if(userEntity.isPresent()) {
            if(list != null){
            res = ResponseFrame.of(list, "내 주문 불러오기을 성공했습니다.");
            }else{
                res = ResponseFrame.of(false, "주문이 없어 내 주문 불러오기을 실패했습니다.");
            }
        }else{
            res = ResponseFrame.of(false, "사용자가 없어 내 주문 불러오기을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     * 상품 인수 상태 변경
     *
     * @param orderStateRequestDto
     * @return Object
     */

    @ApiOperation(value="상품 인수 상태 변경",response = Object.class)
    @PutMapping("")
    public ResponseEntity<?> updateProductState(@RequestBody OrderStateRequestDto orderStateRequestDto){
        boolean success = orderService.updateProductState(orderStateRequestDto);
        ResponseFrame<?> res;

        if(success) {
            res = ResponseFrame.of(success, "상품 인수 상태 변경을 성공했습니다.");
        }else{
            res = ResponseFrame.of(success, "주문이 없어 상품 인수 상태 변경을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     * 아이들 주문 상세 내역 불러오기
     *
     * @param
     * @return Object
     */

    @ApiOperation(value="아이들 주문 상세 내역 불러오기",response = Object.class)
    @GetMapping("/child/list")
    public ResponseEntity<?> getChildOrderInfo(){
        List<OrderChildResponseDto> list = orderService.getChildOrderInfo();
        ResponseFrame<?> res;

        if(list.size()==0) {
                res = ResponseFrame.of(false, "아이들 주문 리스트가 없어 아이들 주문 상세 내역 불러오기을 실패했습니다.");
        }else{
                res = ResponseFrame.of(list, "아이들 주문 상세 내역 불러오기을 성공했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     * 주문 해쉬 불러오기
     *
     * @param orderDetailSeq
     * @return Object
     */

    @ApiOperation(value="주문 해쉬 불러오기",response = Object.class)
    @GetMapping("/detail/{order-detail-seq}")
    public ResponseEntity<?> getOrderHash(@PathVariable("order-detail-seq") long orderDetailSeq){
        OrderDetailResponseDto orderDetailResponseDto = orderService.getOrderHash(orderDetailSeq);
        ResponseFrame<?> res;

        if(orderDetailResponseDto == null) {
            res = ResponseFrame.of(false, "주문이 없어 주문 해쉬 불러오기를 실패했습니다.");
        }else{
            res = ResponseFrame.of(orderDetailResponseDto, "주문 해쉬 불러오기를 성공했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
