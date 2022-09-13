package com.backend.birdmeal.mapper.user;

import com.backend.birdmeal.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.backend.birdmeal.entity.user.UserEntity;
import com.backend.birdmeal.mapper.StructMapper;

@Mapper
public interface UserMapper extends StructMapper<UserDto, UserEntity> {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Override
    UserEntity toEntity(final UserDto userDto);

    @Override
    UserDto toDto(final UserEntity userEntity);
}
