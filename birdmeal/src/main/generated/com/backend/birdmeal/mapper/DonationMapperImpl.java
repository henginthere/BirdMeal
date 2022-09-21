package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.DonationDto;
import com.backend.birdmeal.dto.DonationDto.DonationDtoBuilder;
import com.backend.birdmeal.entity.DonationEntity;
import com.backend.birdmeal.entity.DonationEntity.DonationEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-21T14:53:39+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
public class DonationMapperImpl implements DonationMapper {

    @Override
    public DonationEntity toEntity(DonationDto donationDto) {
        if ( donationDto == null ) {
            return null;
        }

        DonationEntityBuilder donationEntity = DonationEntity.builder();

        donationEntity.donationSeq( donationDto.getDonationSeq() );
        donationEntity.userSeq( donationDto.getUserSeq() );
        donationEntity.donationPrice( donationDto.getDonationPrice() );
        donationEntity.donationDate( donationDto.getDonationDate() );
        donationEntity.donationType( donationDto.isDonationType() );

        return donationEntity.build();
    }

    @Override
    public DonationDto toDto(DonationEntity donationEntity) {
        if ( donationEntity == null ) {
            return null;
        }

        DonationDtoBuilder donationDto = DonationDto.builder();

        donationDto.donationSeq( donationEntity.getDonationSeq() );
        donationDto.userSeq( donationEntity.getUserSeq() );
        donationDto.donationPrice( donationEntity.getDonationPrice() );
        donationDto.donationDate( donationEntity.getDonationDate() );
        donationDto.donationType( donationEntity.isDonationType() );

        return donationDto.build();
    }

    @Override
    public List<DonationDto> toDtoList(List<DonationEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DonationDto> list = new ArrayList<DonationDto>( entityList.size() );
        for ( DonationEntity donationEntity : entityList ) {
            list.add( toDto( donationEntity ) );
        }

        return list;
    }

    @Override
    public List<DonationEntity> toEntityList(List<DonationDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DonationEntity> list = new ArrayList<DonationEntity>( dtoList.size() );
        for ( DonationDto donationDto : dtoList ) {
            list.add( toEntity( donationDto ) );
        }

        return list;
    }
}
