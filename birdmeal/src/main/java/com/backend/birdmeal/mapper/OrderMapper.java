package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.OrderDto;
import com.backend.birdmeal.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper extends StructMapper<OrderDto, OrderEntity> {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    @Override
    OrderEntity toEntity(final OrderDto orderDto);

    @Override
    OrderDto toDto(final OrderEntity orderEntity);

    @Override
    List<OrderDto> toDtoList(List<OrderEntity> entityList);
}
