package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.RegistUserDto;
import com.backend.birdmeal.dto.ResponseLoginDto;
import com.backend.birdmeal.dto.UpdateUserDto;
import com.backend.birdmeal.entity.AuthorityEntity;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.AuthorityRepository;
import com.backend.birdmeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    final UserRepository userRepository;
    final AuthorityRepository authorityRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    public boolean signup(RegistUserDto registUserDto){


        //이메일 주소가 중복이면 false
        if (userRepository.findByUserEmail(registUserDto.getUserEmail()).isPresent()) {
            return false;
        }
        boolean role;
        String pass = registUserDto.getUserEmail().split("@")[0];

        AuthorityEntity authority;

        //권한 확인
        if(!registUserDto.isUserRole()){
            role = false;
            authority = AuthorityEntity.builder()
                    .authorityName("ROLE_ADMIN")
                    .build();
        }
        else{
            role = true;
            authority = AuthorityEntity.builder()
                    .authorityName("ROLE_CHILD")
                    .build();
        }


        authorityRepository.save(authority);

        UserEntity userEntity = UserEntity.builder()
                .userSeq(0)
                .userEmail(registUserDto.getUserEmail())
                .userNickname(registUserDto.getUserNickname())
                .userPass(passwordEncoder.encode(pass))
                .userRole(role)
                .authorities(Collections.singleton(authority))
                .build();


        userRepository.save(userEntity);

        return true;
    }

    public ResponseLoginDto login(String userEmail){

        if (userRepository.findByUserEmail(userEmail)== null) {
            return null;
        }
        ResponseLoginDto responseLoginDto = authService.authorize(userEmail);

        return responseLoginDto;

    }

    public boolean updateUser(Long userSeq, UpdateUserDto updateUserDto){
        Optional<UserEntity> userOptional = userRepository.findByUserSeq(userSeq);

        if(userOptional.isEmpty()){
            return false;
        }

        UserEntity user = userOptional.get();

        user.setUserAdd(updateUserDto.getUserAdd());
        user.setUserTel(updateUserDto.getUserTel());

        return true;
    }
}
