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
    date = "2022-10-04T11:08:49+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public List<UserDto> toDtoList(List<UserEntity> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( arg0.size() );
        for ( UserEntity userEntity : arg0 ) {
            list.add( toDto( userEntity ) );
        }

        return list;
    }

    @Override
    public List<UserEntity> toEntityList(List<UserDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<UserEntity> list = new ArrayList<UserEntity>( arg0.size() );
        for ( UserDto userDto : arg0 ) {
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
        userEntity.userPass( userDto.getUserPass() );
        userEntity.userRole( userDto.isUserRole() );
        userEntity.userNickname( userDto.getUserNickname() );
        userEntity.userEoa( userDto.getUserEoa() );
        userEntity.userTel( userDto.getUserTel() );
        userEntity.userAdd( userDto.getUserAdd() );
        userEntity.userAddDetail( userDto.getUserAddDetail() );
        userEntity.userIsMint( userDto.isUserIsMint() );
        userEntity.userMonthMoney( userDto.getUserMonthMoney() );
        userEntity.userChargeState( userDto.isUserChargeState() );
        userEntity.userRegistDate( userDto.getUserRegistDate() );
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
        userDto.userPass( userEntity.getUserPass() );
        userDto.userNickname( userEntity.getUserNickname() );
        userDto.userEoa( userEntity.getUserEoa() );
        userDto.userTel( userEntity.getUserTel() );
        userDto.userAdd( userEntity.getUserAdd() );
        userDto.userAddDetail( userEntity.getUserAddDetail() );
        userDto.userIsMint( userEntity.isUserIsMint() );
        userDto.userMonthMoney( userEntity.getUserMonthMoney() );
        userDto.userChargeState( userEntity.isUserChargeState() );
        userDto.userRegistDate( userEntity.getUserRegistDate() );
        userDto.userUpdateDate( userEntity.getUserUpdateDate() );

        return userDto.build();
    }
}
