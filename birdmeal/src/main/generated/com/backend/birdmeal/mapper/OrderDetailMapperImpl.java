package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.OrderDetailDto;
import com.backend.birdmeal.dto.OrderDetailDto.OrderDetailDtoBuilder;
import com.backend.birdmeal.entity.OrderDetailEntity;
import com.backend.birdmeal.entity.OrderDetailEntity.OrderDetailEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-16T00:01:20+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public List<OrderDetailEntity> toEntityList(List<OrderDetailDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OrderDetailEntity> list = new ArrayList<OrderDetailEntity>( dtoList.size() );
        for ( OrderDetailDto orderDetailDto : dtoList ) {
            list.add( toEntity( orderDetailDto ) );
        }

        return list;
    }

    @Override
    public OrderDetailEntity toEntity(OrderDetailDto orderDetailDto) {
        if ( orderDetailDto == null ) {
            return null;
        }

        OrderDetailEntityBuilder orderDetailEntity = OrderDetailEntity.builder();

        orderDetailEntity.orderDetailSeq( orderDetailDto.getOrderDetailSeq() );
        orderDetailEntity.orderSeq( orderDetailDto.getOrderSeq() );
        orderDetailEntity.productSeq( orderDetailDto.getProductSeq() );
        orderDetailEntity.sellerSeq( orderDetailDto.getSellerSeq() );
        orderDetailEntity.orderQuantity( orderDetailDto.getOrderQuantity() );
        orderDetailEntity.orderTHash( orderDetailDto.getOrderTHash() );
        orderDetailEntity.orderToState( orderDetailDto.isOrderToState() );
        orderDetailEntity.orderDeliveryNumber( orderDetailDto.getOrderDeliveryNumber() );
        orderDetailEntity.orderDeliveryCompany( orderDetailDto.getOrderDeliveryCompany() );

        return orderDetailEntity.build();
    }

    @Override
    public OrderDetailDto toDto(OrderDetailEntity orderDetailEntity) {
        if ( orderDetailEntity == null ) {
            return null;
        }

        OrderDetailDtoBuilder orderDetailDto = OrderDetailDto.builder();

        orderDetailDto.orderDetailSeq( orderDetailEntity.getOrderDetailSeq() );
        orderDetailDto.orderSeq( orderDetailEntity.getOrderSeq() );
        orderDetailDto.productSeq( orderDetailEntity.getProductSeq() );
        orderDetailDto.sellerSeq( orderDetailEntity.getSellerSeq() );
        orderDetailDto.orderQuantity( orderDetailEntity.getOrderQuantity() );
        orderDetailDto.orderTHash( orderDetailEntity.getOrderTHash() );
        orderDetailDto.orderToState( orderDetailEntity.isOrderToState() );
        orderDetailDto.orderDeliveryNumber( orderDetailEntity.getOrderDeliveryNumber() );
        orderDetailDto.orderDeliveryCompany( orderDetailEntity.getOrderDeliveryCompany() );

        return orderDetailDto.build();
    }

    @Override
    public List<OrderDetailDto> toDtoList(List<OrderDetailEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderDetailDto> list = new ArrayList<OrderDetailDto>( entityList.size() );
        for ( OrderDetailEntity orderDetailEntity : entityList ) {
            list.add( toDto( orderDetailEntity ) );
        }

        return list;
    }
}
