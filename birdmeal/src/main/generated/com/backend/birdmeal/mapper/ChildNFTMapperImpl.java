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
    date = "2022-09-30T14:37:25+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class ChildNFTMapperImpl implements ChildNFTMapper {

    @Override
    public ChildNFTEntity toEntity(ChildNFTDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ChildNFTEntityBuilder childNFTEntity = ChildNFTEntity.builder();

        childNFTEntity.nftSeq( arg0.getNftSeq() );
        childNFTEntity.userSeq( arg0.getUserSeq() );
        childNFTEntity.nftImg( arg0.getNftImg() );
        childNFTEntity.nftCnt( arg0.getNftCnt() );
        childNFTEntity.nftCreateDate( arg0.getNftCreateDate() );

        return childNFTEntity.build();
    }

    @Override
    public ChildNFTDto toDto(ChildNFTEntity arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ChildNFTDtoBuilder childNFTDto = ChildNFTDto.builder();

        childNFTDto.nftSeq( arg0.getNftSeq() );
        childNFTDto.userSeq( arg0.getUserSeq() );
        childNFTDto.nftImg( arg0.getNftImg() );
        childNFTDto.nftCnt( arg0.getNftCnt() );
        childNFTDto.nftCreateDate( arg0.getNftCreateDate() );

        return childNFTDto.build();
    }

    @Override
    public List<ChildNFTEntity> toEntityList(List<ChildNFTDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ChildNFTEntity> list = new ArrayList<ChildNFTEntity>( arg0.size() );
        for ( ChildNFTDto childNFTDto : arg0 ) {
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
