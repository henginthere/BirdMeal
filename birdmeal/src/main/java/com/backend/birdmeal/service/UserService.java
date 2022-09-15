package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.RegistUserDto;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    final UserRepository userRepository;

    public boolean signup(RegistUserDto registUserDto){


        //이메일 주소가 중복이면 false
        if (userRepository.findByUserEmail(registUserDto.getUserEmail())!= null) {
            return false;
        }

        UserEntity userEntity = UserEntity.builder()
                .userSeq(0)
                .userEmail(registUserDto.getUserEmail())
                .userNickname(registUserDto.getUserNickname())
                .userAuthority(registUserDto.getUserAuthority())
                .build();


        userRepository.save(userEntity);

        return true;
    }
}
