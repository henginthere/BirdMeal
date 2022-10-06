package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.ChildNFTDto;
import com.backend.birdmeal.entity.ChildNFTEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChildNFTMapper extends StructMapper<ChildNFTDto, ChildNFTEntity> {
    ChildNFTMapper MAPPER = Mappers.getMapper(ChildNFTMapper.class);

    @Override
    List<ChildNFTDto> toDtoList(List<ChildNFTEntity> entityList);
}
