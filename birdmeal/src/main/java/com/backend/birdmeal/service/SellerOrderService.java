package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.*;
import com.backend.birdmeal.entity.OrderDetailEntity;
import com.backend.birdmeal.repository.OrderDetailRepository;
import com.backend.birdmeal.repository.OrderRepository;
import com.backend.birdmeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerOrderService {


    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

//    // 구매 내역 목록 보기
//    public List<SellerOrderResponseDto> getSellerOrderProduct(long sellerSeq) {
//        List<SellerOrderResponseDto> sellerOrderResponseDtoList = new ArrayList<>();
//
//        // sellerSeq로 주문 목록 찾기
//        List<OrderEntity> orderEntityList = orderRepository.findAllBySellerSeq(sellerSeq);
//
//        // Entity -> Dto
//        List<OrderDto> orderDtoList = OrderMapper.MAPPER.toDtoList(orderEntityList);
//
//        // 주문 번호를 가지고 주문 상세 찾고, 상품 찾고, responseList에 넣기
//        for(int i=0; i<orderDtoList.size(); i++){
//            // 주문 번호
//            long orderSeq = orderDtoList.get(i).getOrderSeq();
//
//            // 주문 넣기
//            SellerOrderResponseDto sellerOrderResponseDto = new SellerOrderResponseDto();
//            sellerOrderResponseDto.setOrderDto(orderDtoList.get(i));
//
//            // 상세 주문 List 구하기
//            // 구하면서 상품도 같이 저장
//            List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findAllByOrderSeq(orderSeq);
//
//            // Entity -> Dto
//            List<OrderDetailDto> orderDetailDtoList = OrderDetailMapper.MAPPER.toDtoList(orderDetailEntityList);
//
//            List<SellerOrderDetailReaponseDto> sellerOrderDetailReaponseDtoList = new ArrayList<>();
//
//            // 상세주문 List 돌면서 넣기
//            for(int j=0; j<orderDetailDtoList.size(); j++) {
//                // 상품Seq
//                long productSeq = orderDetailDtoList.get(j).getProductSeq();
//
//                // 상품 정보 찾기
//                ProductEntity productEntity = productRepository.findByProductSeq(productSeq);
//
//                // Entity -> Dto
//                ProductDto productDto = ProductMapper.MAPPER.toDto(productEntity);
//
//
//                // 상세 주문, 상품 리스트 만들기
//                SellerOrderDetailReaponseDto sellerOrderDetailReaponseDto = new SellerOrderDetailReaponseDto();
//                sellerOrderDetailReaponseDto.setOrderDetailDto(orderDetailDtoList.get(j));
//                sellerOrderDetailReaponseDto.setProductDto(productDto);
//
//                // 상세 주문, 상품 List 넣기
//                sellerOrderDetailReaponseDtoList.add(sellerOrderDetailReaponseDto);
//            }
//
//            // 상세주문, 상품 List를 ResponseDto에 넣기
//            sellerOrderResponseDto.setSellerOrderDetailReaponseDtoList(sellerOrderDetailReaponseDtoList);
//
//            // list에 추가
//            sellerOrderResponseDtoList.add(sellerOrderResponseDto);
//        }
//
//        return sellerOrderResponseDtoList;
//    }

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
