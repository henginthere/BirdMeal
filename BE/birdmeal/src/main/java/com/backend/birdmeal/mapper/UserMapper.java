package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.backend.birdmeal.entity.UserEntity;

@Mapper
public interface UserMapper extends StructMapper<UserDto, UserEntity> {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Override
    UserEntity toEntity(final UserDto userDto);

    @Override
    UserDto toDto(final UserEntity userEntity);
}
