package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.OrderRequestDto;
import com.backend.birdmeal.entity.OrderDetailEntity;
import com.backend.birdmeal.entity.OrderEntity;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.repository.OrderDetailRepository;
import com.backend.birdmeal.repository.OrderRepository;
import com.backend.birdmeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public boolean setOrderInfo(List<OrderRequestDto> orderRequestList) {

        // 리스트가 없으면 주문 실패
        if(orderRequestList.size()==0) return false;

        // 주문 총 가격
        int sum =0;

        // 주문 Seq
        long orderSeq = 0;

        // 주문 Table에 컬럼 생성하기 - 1개
        OrderEntity orderEntity = OrderEntity.builder()
                    .orderSeq(0L)
                    .userSeq(orderRequestList.get(0).getUserSeq())
                    .build();

        // 저장하기
        orderRepository.save(orderEntity);

        for(int i=0; i<orderRequestList.size();i++) {
            OrderRequestDto orderRequestDto = orderRequestList.get(i);

            // 상품이 없으면 주문 실패
            ProductEntity productEntity = productRepository.findByProductSeq(orderRequestDto.getProductSeq());
            if (productEntity == null || productEntity.isProductIsDeleted()) return false;

            // 주문 총 가격 구해주기 ( 상품 가격 * 구매 수량 )
            sum += (orderRequestDto.getOrderQuantity() * productEntity.getProductPrice());

            // 주문 Seq 가져오기
            List<OrderEntity> list = orderRepository.findAll();
            orderSeq = list.get(list.size() - 1).getOrderSeq();

            // 주문 Detail Table에 컬럼 생성하기
            OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
                    .orderSeq(orderSeq)
                    .productSeq(orderRequestDto.getProductSeq())
                    .sellerSeq(productEntity.getSellerSeq())
                    .orderQuantity(orderRequestDto.getOrderQuantity())
                    .orderTHash(orderRequestDto.getOrderTHash())
                    .orderToState(false)
                    .build();

            // 저장하기
            orderDetailRepository.save(orderDetailEntity);
        }

        // 총 가격 넣고 한 번 더 저장
        OrderEntity orderEntityUpdate = orderRepository.findByOrderSeq(orderSeq);
        orderEntityUpdate.setOrderPrice(sum);
        // 저장하기
        orderRepository.save(orderEntityUpdate);

        return true;
    }
}
