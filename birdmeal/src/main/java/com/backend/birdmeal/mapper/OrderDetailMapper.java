package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.OrderDetailDto;
import com.backend.birdmeal.entity.OrderDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderDetailMapper extends StructMapper<OrderDetailDto, OrderDetailEntity> {
    OrderDetailMapper MAPPER = Mappers.getMapper(OrderDetailMapper.class);

    @Override
    OrderDetailEntity toEntity(final OrderDetailDto orderDetailDto);

    @Override
    OrderDetailDto toDto(final OrderDetailEntity orderDetailEntity);

    @Override
    List<OrderDetailDto> toDtoList(List<OrderDetailEntity> entityList);
}
