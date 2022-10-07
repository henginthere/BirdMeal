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
    date = "2022-10-06T02:29:23+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public List<OrderDetailEntity> toEntityList(List<OrderDetailDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<OrderDetailEntity> list = new ArrayList<OrderDetailEntity>( arg0.size() );
        for ( OrderDetailDto orderDetailDto : arg0 ) {
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
