package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.SellerDto;
import com.backend.birdmeal.dto.SellerDto.SellerDtoBuilder;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.entity.SellerEntity.SellerEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-20T18:00:17+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class SellerMapperImpl implements SellerMapper {

    @Override
    public List<SellerDto> toDtoList(List<SellerEntity> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SellerDto> list = new ArrayList<SellerDto>( arg0.size() );
        for ( SellerEntity sellerEntity : arg0 ) {
            list.add( toDto( sellerEntity ) );
        }

        return list;
    }

    @Override
    public List<SellerEntity> toEntityList(List<SellerDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SellerEntity> list = new ArrayList<SellerEntity>( arg0.size() );
        for ( SellerDto sellerDto : arg0 ) {
            list.add( toEntity( sellerDto ) );
        }

        return list;
    }

    @Override
    public SellerEntity toEntity(SellerDto sellerDto) {
        if ( sellerDto == null ) {
            return null;
        }

        SellerEntityBuilder sellerEntity = SellerEntity.builder();

        sellerEntity.sellerSeq( sellerDto.getSellerSeq() );
        sellerEntity.sellerEmail( sellerDto.getSellerEmail() );
        sellerEntity.sellerNickname( sellerDto.getSellerNickname() );
        sellerEntity.sellerTel( sellerDto.getSellerTel() );
        sellerEntity.sellerAddress( sellerDto.getSellerAddress() );
        sellerEntity.sellerInfo( sellerDto.getSellerInfo() );
        sellerEntity.sellerCreateDate( sellerDto.getSellerCreateDate() );
        sellerEntity.sellerUpdateDate( sellerDto.getSellerUpdateDate() );

        return sellerEntity.build();
    }

    @Override
    public SellerDto toDto(SellerEntity sellerEntity) {
        if ( sellerEntity == null ) {
            return null;
        }

        SellerDtoBuilder sellerDto = SellerDto.builder();

        sellerDto.sellerSeq( sellerEntity.getSellerSeq() );
        sellerDto.sellerEmail( sellerEntity.getSellerEmail() );
        sellerDto.sellerNickname( sellerEntity.getSellerNickname() );
        sellerDto.sellerTel( sellerEntity.getSellerTel() );
        sellerDto.sellerAddress( sellerEntity.getSellerAddress() );
        sellerDto.sellerInfo( sellerEntity.getSellerInfo() );
        sellerDto.sellerCreateDate( sellerEntity.getSellerCreateDate() );
        sellerDto.sellerUpdateDate( sellerEntity.getSellerUpdateDate() );

        return sellerDto.build();
    }
}
