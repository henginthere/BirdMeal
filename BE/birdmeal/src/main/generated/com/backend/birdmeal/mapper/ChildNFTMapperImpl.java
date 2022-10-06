package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.ChildNFTDto;
import com.backend.birdmeal.dto.ChildNFTDto.ChildNFTDtoBuilder;
import com.backend.birdmeal.entity.ChildNFTEntity;
import com.backend.birdmeal.entity.ChildNFTEntity.ChildNFTEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-05T10:13:57+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
public class ChildNFTMapperImpl implements ChildNFTMapper {

    @Override
    public ChildNFTEntity toEntity(ChildNFTDto dto) {
        if ( dto == null ) {
            return null;
        }

        ChildNFTEntityBuilder childNFTEntity = ChildNFTEntity.builder();

        childNFTEntity.nftSeq( dto.getNftSeq() );
        childNFTEntity.userSeq( dto.getUserSeq() );
        childNFTEntity.nftImg( dto.getNftImg() );
        childNFTEntity.nftCnt( dto.getNftCnt() );
        childNFTEntity.nftCreateDate( dto.getNftCreateDate() );

        return childNFTEntity.build();
    }

    @Override
    public ChildNFTDto toDto(ChildNFTEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ChildNFTDtoBuilder childNFTDto = ChildNFTDto.builder();

        childNFTDto.nftSeq( entity.getNftSeq() );
        childNFTDto.userSeq( entity.getUserSeq() );
        childNFTDto.nftImg( entity.getNftImg() );
        childNFTDto.nftCnt( entity.getNftCnt() );
        childNFTDto.nftCreateDate( entity.getNftCreateDate() );

        return childNFTDto.build();
    }

    @Override
    public List<ChildNFTEntity> toEntityList(List<ChildNFTDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ChildNFTEntity> list = new ArrayList<ChildNFTEntity>( dtoList.size() );
        for ( ChildNFTDto childNFTDto : dtoList ) {
            list.add( toEntity( childNFTDto ) );
        }

        return list;
    }

    @Override
    public List<ChildNFTDto> toDtoList(List<ChildNFTEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ChildNFTDto> list = new ArrayList<ChildNFTDto>( entityList.size() );
        for ( ChildNFTEntity childNFTEntity : entityList ) {
            list.add( toDto( childNFTEntity ) );
        }

        return list;
    }
}
