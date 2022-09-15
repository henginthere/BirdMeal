package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.MyOrderResponseDto;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;



    // 주문 내역 저장
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


    // 내 주문 불러오기
    public List<MyOrderResponseDto> getMyOrderInfo(long userSeq) {
        // 반환할 리스트 생성
        List<MyOrderResponseDto> responseList = new ArrayList<>();

        // 주문 개수 구하기
        List<OrderEntity> orderEntityList = orderRepository.findAllByUserSeqOrderByOrderDateDesc(userSeq);

        // 주문 개수
        int orderCnt = orderEntityList.size();
        System.out.println("주문 개수 : " + orderCnt);

        // 주문 개수만큼 돌면서 ResponseDto 채우기
        for(int i=0; i<orderCnt; i++){
            MyOrderResponseDto myOrderListResponseDto = new MyOrderResponseDto();

            // 주문 가져오기
            OrderEntity orderEntity = orderEntityList.get(i);

            // 주문 정보 저장
            myOrderListResponseDto.setOrderSeq(orderEntity.getOrderSeq());
            myOrderListResponseDto.setUserSeq(orderEntity.getUserSeq());
            myOrderListResponseDto.setOrderPrice(orderEntity.getOrderPrice());
            myOrderListResponseDto.setOrderDate(orderEntity.getOrderDate());

            // 주문 번호로 주문 상세 가져오기
            List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findAllByOrderSeq(orderEntity.getOrderSeq());

            // 주문 상세가 없으면 통과
            if(orderDetailEntityList.size() == 0) continue;

            System.out.println("상세주문개수 : " + orderDetailEntityList.size());

            // 제일 처음 주문 상품 저장
            OrderDetailEntity orderDetailEntity = orderDetailEntityList.get(0);
            ProductEntity productEntity = productRepository.findByProductSeq(orderDetailEntity.getProductSeq());


            // 상세 주문 저장
            myOrderListResponseDto.setOrderFirstName(productEntity.getProductName());
            myOrderListResponseDto.setProductThumbnailImg(productEntity.getProductThumbnailImg());

            // 주문 건수 저장
            long cnt = orderDetailEntityList.size()-1;
            myOrderListResponseDto.setOrderCnt(cnt);

            // 리스트에 저장
            responseList.add(myOrderListResponseDto);
        }

        return responseList;
    }
}
