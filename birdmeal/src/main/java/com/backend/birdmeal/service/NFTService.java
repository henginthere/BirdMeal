package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ChildNFTDto;
import com.backend.birdmeal.dto.NFTImgResponseDto;
import com.backend.birdmeal.entity.ChildNFTEntity;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.ChildNFTRepository;
import com.backend.birdmeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class NFTService {

    private final UserRepository userRepository;
    private final ChildNFTRepository childNFTRepository;
    
    // NFT ImgURI 저장
    public boolean setNFTImg(ChildNFTDto childNFTDto) {

        Optional<UserEntity> userOptional = userRepository.findByUserSeq(childNFTDto.getUserSeq());

        UserEntity userEntity = userOptional.get();

        if(childNFTDto.getNftImg() == null) return false;

        ChildNFTEntity childNFTEntity = ChildNFTEntity.builder()
                .userSeq(childNFTDto.getUserSeq())
                .nftImg(childNFTDto.getNftImg())
                .nftCnt(0)
                .build();

        // 저장
        childNFTRepository.save(childNFTEntity);

        return true;
    }

    // NFT ImgURI 가져오기
    public NFTImgResponseDto getNFTImg(long userSeq) {

        // isMint 상태 바꿔주기
        Optional<UserEntity> userOptional = userRepository.findByUserSeq(userSeq);

        UserEntity userEntity = userOptional.get();

        userEntity.setUserIsMint(false);

        // 랜덤으로 골라주기
        // 사이즈 받기
        int size = childNFTRepository.findAll().size();

        if(size == 0) return null;

        int num = (int)((Math.random()*(size)));

        if(num == 0) num++;

        ChildNFTEntity childNFTEntity = childNFTRepository.findByNftSeq(num);

        NFTImgResponseDto nftImgResponseDto = NFTImgResponseDto.builder()
                .nftImg(childNFTEntity.getNftImg())
                .nftSeq(childNFTEntity.getNftSeq())
                .userSeq(childNFTEntity.getUserSeq())
                .build();

        return nftImgResponseDto;
    }
}
