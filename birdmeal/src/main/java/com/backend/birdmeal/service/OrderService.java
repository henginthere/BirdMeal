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
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderChildRepository orderChildRepository;
    private final UserRepository userRepository;




    // 주문 내역 저장
    public boolean setOrderInfo(List<OrderRequestDto> orderRequestList) {
        // 아이인지 아닌지 확인
        Optional<UserEntity> userEntity = userRepository.findByUserSeq(orderRequestList.get(0).getUserSeq());

        boolean isChild = false;

        if(userEntity.get().getUserRole()) isChild = true;

        // 리스트가 없으면 주문 실패
        if(orderRequestList.size()==0) return false;

        // 주문 총 가격
        int sum =0;

        // 주문 Seq
        long orderSeq = 0;

        // 만약 상품이 없다면 false ( 처음 것 체크 )
        ProductEntity productEntityCheck = productRepository.findByProductSeq(orderRequestList.get(0).getProductSeq());
        if(productEntityCheck == null || productEntityCheck.isProductIsDeleted()) return false;

        // 주문 Table에 컬럼 생성하기 - 1개
        OrderEntity orderEntity = OrderEntity.builder()
                    .orderSeq(0)
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

            // 만약 아이가 주문한 것이라면 아이들 주문 Table에 저장하기
            if(isChild){
                OrderChildEntity orderChildEntity = OrderChildEntity.builder()
                        .orderChildDetailSeq(0)
                        .userSeq(userEntity.get().getUserSeq())
                        .userNickname(userEntity.get().getUserNickname())
                        .orderQuantity(orderRequestDto.getOrderQuantity())
                        .productName(productEntity.getProductName())
                        .productPrice(productEntity.getProductPrice())
                        .productThumbnailImg(productEntity.getProductThumbnailImg())
                        .build();

                // 저장하기
                orderChildRepository.save(orderChildEntity);
            }

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
        List<OrderEntity> orderEntityList = orderRepository.findAllByUserSeqOrderByOrderSeqDesc(userSeq);

        // 주문 개수
        int orderCnt = orderEntityList.size();

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

    // 상품 인수 상태 변경
    public boolean updateProductState(OrderStateRequestDto orderStateRequestDto) {
        // 주문이 없으면 false
        OrderDetailEntity orderDetailEntity = orderDetailRepository.findByOrderDetailSeq(orderStateRequestDto.getOrderDetailSeq());
        if(orderDetailEntity == null) return false;

        // 주문 상세 리스트 받아오기
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findAllByOrderDetailSeq(orderStateRequestDto.getOrderDetailSeq());
        for(int i=0; i<orderDetailEntityList.size(); i++){
            // 상태 바꿔주기
            orderDetailEntityList.get(i).setOrderToState(orderStateRequestDto.isOrderToState());

            // 저장
            orderDetailRepository.save(orderDetailEntityList.get(i));
        }

        return true;
    }

    // 내 주문 상세 내역 불러오기
    public List<MyOrderDetailResponseDto> getMyOrderDetailInfo(long userSeq, long orderSeq) {
        // 만약 주문이 내가 한 것이 아니라면 null
        OrderEntity orderEntityCheck = orderRepository.findByOrderSeq(orderSeq);
        if(orderEntityCheck.getUserSeq() != userSeq) return null;

        List<MyOrderDetailResponseDto> myOrderDetailResponseDtoList = new ArrayList<>();

        // 사용자 번호와 주문 번호로 List 개수 구하기
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findAllByOrderSeqOrderByOrderDetailSeqDesc(orderSeq);
        int size = orderDetailEntityList.size();

        if(size==0) return null;

        // 주문디테일 리스트 개수만큼 돌면서
        for(int i=0; i<size; i++){
            // 주문 디테일 가져오기
            OrderDetailEntity orderDetailEntity = orderDetailEntityList.get(i);
            
            // 주문 가져오기
            OrderEntity orderEntity = orderRepository.findByOrderSeq(orderDetailEntity.getOrderSeq());

            // 상품 정보 가져오기
            ProductEntity productEntity = productRepository.findByProductSeq(orderDetailEntity.getProductSeq());

            MyOrderDetailResponseDto myOrderDetailResponseDto = MyOrderDetailResponseDto.builder()
                    .orderDetailSeq(orderDetailEntity.getOrderDetailSeq())
                    .productName(productEntity.getProductName())
                    .productPrice(productEntity.getProductPrice())
                    .orderQuantity(orderDetailEntity.getOrderQuantity())
                    .orderDate(orderEntity.getOrderDate())
                    .orderToState(orderDetailEntity.isOrderToState())
                    .orderDeliveryNumber(orderDetailEntity.getOrderDeliveryNumber())
                    .orderDeliveryCompany(orderDetailEntity.getOrderDeliveryCompany())
                    .productThumbnailImg(productEntity.getProductThumbnailImg())
                    .orderTHash(orderDetailEntity.getOrderTHash())
                    .build();

            myOrderDetailResponseDtoList.add(myOrderDetailResponseDto);
        }
        return myOrderDetailResponseDtoList;
    }

    // 아이들 주문 상세 내역 불러오기
    public List<OrderChildResponseDto> getChildOrderInfo() {
        // 리턴 List ( 최신날짜 순 )
        List<OrderChildEntity> orderChildEntityList = orderChildRepository.findAllByOrderByOrderDateDesc();
        List<OrderChildResponseDto> orderChildResponseDtoList = new ArrayList<>();

        // 아이들 거래 횟수
        int size = orderChildEntityList.size();

        // Entity -> Dto
        for(int i=0; i<size; i++){
            OrderChildEntity orderChildEntity = orderChildEntityList.get(i);

            OrderChildResponseDto orderChildResponseDto = OrderChildResponseDto.builder()
                    .userNickname(orderChildEntity.getUserNickname())
                    .productName(orderChildEntity.getProductName())
                    .productPrice(orderChildEntity.getProductPrice())
                    .productThumbnailImg(orderChildEntity.getProductThumbnailImg())
                    .orderQuantity(orderChildEntity.getOrderQuantity())
                    .orderDate(orderChildEntity.getOrderDate())
                    .orderChildDetailSeq(orderChildEntity.getOrderChildDetailSeq())
                    .build();

            orderChildResponseDtoList.add(orderChildResponseDto);
        }

        return orderChildResponseDtoList;
    }

    public OrderDetailResponseDto getOrderHash(long orderDetailSeq) {
        OrderDetailEntity orderDetailEntity = orderDetailRepository.findByOrderDetailSeq(orderDetailSeq);

        if(orderDetailEntity == null) return null;

        // 상품

        ProductEntity productEntity = productRepository.findByProductSeq(orderDetailEntity.getProductSeq());

        OrderDetailResponseDto orderDetailResponseDto = OrderDetailResponseDto.builder()
                .orderQuantity(orderDetailEntity.getOrderQuantity())
                .productPrice(productEntity.getProductPrice())
                .orderDetailSeq(orderDetailEntity.getOrderDetailSeq())
                .orderTHash(orderDetailEntity.getOrderTHash())
                .productCa(productEntity.getProductCa())
                .build();

        return orderDetailResponseDto;
    }
}
