package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.*;
import com.backend.birdmeal.entity.*;
import com.backend.birdmeal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerOrderService {


    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    // 구매 내역 목록 보기
    public List<SellerOrderResponseDto> getSellerOrderProduct(long sellerSeq) {
        // 리턴할 List
        List<SellerOrderResponseDto> sellerOrderResponseDtoList = new ArrayList<>();

        // 모든 orderDetail 찾기
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findAllBySellerSeq(sellerSeq);

        int size = orderDetailEntityList.size();

        // 리스트 크기만큼 돌면서 반환Dto 완성하기
        for(int i=0; i<size; i++){
            // orderDetail 가져오기
            OrderDetailEntity orderDetailEntity = orderDetailEntityList.get(i);

            // order 가져오기
            OrderEntity orderEntity = orderRepository.findByOrderSeq(orderDetailEntity.getOrderSeq());

            // category 가져오기
            CategoryEntity categoryEntity = categoryRepository.findByCategorySeq(orderDetailEntity.getCategorySeq());

            // user 가져오기
            Optional<UserEntity> userOptional = userRepository.findByUserSeq(orderEntity.getUserSeq());
            UserEntity userEntity = userOptional.get();

            // 가격
            int price = orderDetailEntity.getProductPrice() * orderDetailEntity.getOrderQuantity();

            // 반환값 만들기
            SellerOrderResponseDto sellerOrderResponseDto = SellerOrderResponseDto.builder()
                    .orderSeq(orderEntity.getOrderSeq())
                    .userSeq(orderEntity.getUserSeq())
                    .userNickname(userEntity.getUserNickname())
                    .userTel(userEntity.getUserTel())
                    .userAdd(userEntity.getUserAdd())
                    .userAddDetail(userEntity.getUserAddDetail())
                    .orderPrice(price)
                    .orderDetailSeq(orderDetailEntity.getOrderDetailSeq())
                    .sellerSeq(orderDetailEntity.getSellerSeq())
                    .orderDate(orderEntity.getOrderDate())
                    .orderQuantity(orderDetailEntity.getOrderQuantity())
                    .orderTHash(orderDetailEntity.getOrderTHash())
                    .orderToState(orderDetailEntity.isOrderToState())
                    .orderDeliveryNumber(orderDetailEntity.getOrderDeliveryNumber())
                    .orderDeliveryCompany(orderDetailEntity.getOrderDeliveryCompany())
                    .categoryName(categoryEntity.getCategoryName())
                    .productName(orderDetailEntity.getProductName())
                    .productPrice(orderDetailEntity.getProductPrice())
                    .productCa(orderDetailEntity.getProductCa())
                    .productThumbnailImg(orderDetailEntity.getProductThumbnailImg())
                    .productIsDeleted(orderDetailEntity.isProductIsDeleted())
                    .orderIsCanceled(orderDetailEntity.isOrderIsCanceled())
                    .orderIsRefunded(orderDetailEntity.isOrderIsRefunded())
                    .build();

            sellerOrderResponseDtoList.add(sellerOrderResponseDto);
        }

        return sellerOrderResponseDtoList;
    }

    // 배송 정보 입력
    public boolean updateSellerOrderInfo(OrderDeatilInfoDto orderDeatilInfoDto){
        OrderDetailEntity orderDetailEntity = orderDetailRepository.findByOrderDetailSeq(orderDeatilInfoDto.getOrderDetailSeq());

        // 주문이 없는 경우 false
        if(orderDetailEntity == null) return false;

        // 배송 정보 입력
        orderDetailEntity.setOrderDeliveryNumber(orderDeatilInfoDto.getOrderDeliveryNumber());
        orderDetailEntity.setOrderDeliveryCompany(orderDeatilInfoDto.getOrderDeliveryCompany());

        // 저장하기
        orderDetailRepository.save(orderDetailEntity);

        return true;
    }
}
