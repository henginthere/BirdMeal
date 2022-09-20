package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.UserDto;
import com.backend.birdmeal.dto.UserDto.UserDtoBuilder;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.entity.UserEntity.UserEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-20T10:25:39+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public List<UserDto> toDtoList(List<UserEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( entityList.size() );
        for ( UserEntity userEntity : entityList ) {
            list.add( toDto( userEntity ) );
        }

        return list;
    }

    @Override
    public List<UserEntity> toEntityList(List<UserDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserEntity> list = new ArrayList<UserEntity>( dtoList.size() );
        for ( UserDto userDto : dtoList ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }

    @Override
    public UserEntity toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userSeq( userDto.getUserSeq() );
        userEntity.userEmail( userDto.getUserEmail() );
        userEntity.userRole( userDto.isUserRole() );
        userEntity.userNickname( userDto.getUserNickname() );
        userEntity.userEoa( userDto.getUserEoa() );
        userEntity.userTel( userDto.getUserTel() );
        userEntity.userAdd( userDto.getUserAdd() );
        userEntity.userChargeState( userDto.isUserChargeState() );
        userEntity.userUpdateDate( userDto.getUserUpdateDate() );

        return userEntity.build();
    }

    @Override
    public UserDto toDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.userSeq( userEntity.getUserSeq() );
        userDto.userEmail( userEntity.getUserEmail() );
        if ( userEntity.getUserRole() != null ) {
            userDto.userRole( userEntity.getUserRole() );
        }
        userDto.userNickname( userEntity.getUserNickname() );
        userDto.userEoa( userEntity.getUserEoa() );
        userDto.userTel( userEntity.getUserTel() );
        userDto.userAdd( userEntity.getUserAdd() );
        userDto.userChargeState( userEntity.isUserChargeState() );
        userDto.userUpdateDate( userEntity.getUserUpdateDate() );

        return userDto.build();
    }
}
