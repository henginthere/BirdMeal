package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.SellerDto;
import com.backend.birdmeal.entity.SellerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellerMapper extends StructMapper<SellerDto, SellerEntity> {
    SellerMapper MAPPER = Mappers.getMapper(SellerMapper.class);

    @Override
    SellerEntity toEntity(final SellerDto sellerDto);

    @Override
    SellerDto toDto(final SellerEntity sellerEntity);


}
