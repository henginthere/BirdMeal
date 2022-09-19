package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SellerProductMapper extends StructMapper<ProductDto, ProductEntity> {
    SellerProductMapper MAPPER = Mappers.getMapper(SellerProductMapper.class);

    @Override
    ProductEntity toEntity(final ProductDto productDto);

    @Override
    ProductDto toDto(final ProductEntity productEntity);

    @Override
    List<ProductDto> toDtoList(List<ProductEntity> entityList);
}