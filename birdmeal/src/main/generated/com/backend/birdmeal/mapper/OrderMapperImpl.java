package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.OrderDto;
import com.backend.birdmeal.dto.OrderDto.OrderDtoBuilder;
import com.backend.birdmeal.entity.OrderEntity;
import com.backend.birdmeal.entity.OrderEntity.OrderEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-19T13:19:33+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public List<OrderEntity> toEntityList(List<OrderDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OrderEntity> list = new ArrayList<OrderEntity>( dtoList.size() );
        for ( OrderDto orderDto : dtoList ) {
            list.add( toEntity( orderDto ) );
        }

        return list;
    }

    @Override
    public OrderEntity toEntity(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        OrderEntityBuilder orderEntity = OrderEntity.builder();

        orderEntity.orderSeq( orderDto.getOrderSeq() );
        orderEntity.userSeq( orderDto.getUserSeq() );
        orderEntity.orderPrice( orderDto.getOrderPrice() );
        orderEntity.orderDate( orderDto.getOrderDate() );

        return orderEntity.build();
    }

    @Override
    public OrderDto toDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.orderSeq( orderEntity.getOrderSeq() );
        orderDto.userSeq( orderEntity.getUserSeq() );
        orderDto.orderPrice( orderEntity.getOrderPrice() );
        orderDto.orderDate( orderEntity.getOrderDate() );

        return orderDto.build();
    }

    @Override
    public List<OrderDto> toDtoList(List<OrderEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( entityList.size() );
        for ( OrderEntity orderEntity : entityList ) {
            list.add( toDto( orderEntity ) );
        }

        return list;
    }
}
