package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.DonationDto;
import com.backend.birdmeal.entity.DonationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface DonationMapper extends StructMapper<DonationDto, DonationEntity> {

    DonationMapper MAPPER = Mappers.getMapper(DonationMapper.class);

    @Override
    DonationEntity toEntity(final DonationDto donationDto);

    @Override
    DonationDto toDto(final DonationEntity donationEntity);

    @Override
    List<DonationDto> toDtoList(List<DonationEntity> entityList);

    @Override
    List<DonationEntity> toEntityList(List<DonationDto> dtoList);
}
