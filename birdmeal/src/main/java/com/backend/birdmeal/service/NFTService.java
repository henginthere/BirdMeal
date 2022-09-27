package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ChildNFTDto;
import com.backend.birdmeal.dto.ChildNFTRequestDto;
import com.backend.birdmeal.dto.NFTImgResponseDto;
import com.backend.birdmeal.entity.ChildNFTEntity;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.ChildNFTRepository;
import com.backend.birdmeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class NFTService {

    private final UserRepository userRepository;
    private final ChildNFTRepository childNFTRepository;
    
    // NFT ImgURI 저장
    public boolean setNFTImg(ChildNFTRequestDto childNFTRequestDto) {

        Optional<UserEntity> userOptional = userRepository.findByUserSeq(childNFTRequestDto.getUserSeq());

        UserEntity userEntity = userOptional.get();

        // 아이들이 아니면 false
        if(!userEntity.getUserRole()) return false;

        // 사진이 없으면 false
        if(childNFTRequestDto.getNftImg() == null) return false;

        ChildNFTEntity childNFTEntity = ChildNFTEntity.builder()
                .userSeq(childNFTRequestDto.getUserSeq())
                .nftImg(childNFTRequestDto.getNftImg())
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

        int num = (int)((Math.random()*(size+1)));

        if(num == 0) num++;

        ChildNFTEntity childNFTEntity = childNFTRepository.findByNftSeq(num);

        NFTImgResponseDto nftImgResponseDto = NFTImgResponseDto.builder()
                .nftImg(childNFTEntity.getNftImg())
                .nftSeq(childNFTEntity.getNftSeq())
                .userSeq(childNFTEntity.getUserSeq())
                .build();

        // 카운팅해주기
        int cnt = childNFTEntity.getNftCnt();
        cnt++;
        childNFTEntity.setNftCnt(cnt);

        childNFTRepository.save(childNFTEntity);

        return nftImgResponseDto;
    }

    // 내가 만든 포토 카드 요청
    public List getMyCardInfo(long userSeq) {

        // 리스트 받기
        List<ChildNFTEntity> childNFTEntityList = childNFTRepository.findAllByUserSeq(userSeq);

        return childNFTEntityList;
    }

    // 사용자 IsMint 상태 바꾸기
    public boolean changeState(long userSeq) {

        Optional<UserEntity> userOptional = userRepository.findByUserSeq(userSeq);

        UserEntity userEntity = userOptional.get();

        if(userEntity.getUserMonthMoney()<100000) return false;

        // 민팅 가능 상태로 바꿔주기
        userEntity.setUserIsMint(true);

        // 저장
        userRepository.save(userEntity);

        return true;
    }
}
