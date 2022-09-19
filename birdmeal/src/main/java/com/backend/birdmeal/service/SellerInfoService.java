package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.*;
import com.backend.birdmeal.entity.AuthorityEntity;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.jwt.TokenProvider;
import com.backend.birdmeal.mapper.SellerMapper;
import com.backend.birdmeal.repository.AuthorityRepository;
import com.backend.birdmeal.repository.SellerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerInfoService {

    private final SellerInfoRepository sellerInfoRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    // 판매자 회원가입
    public boolean signup(RegistSellerDto registSellerDto) {
        // 이메일 정보가 중복이면 false
        if(sellerInfoRepository.findBySellerEmail(registSellerDto.getSellerEmail()) != null){
            return false;
        }
        
        // 이메일 아이디로 비밀번호
        String pass = registSellerDto.getSellerEmail().split("@")[0];

        // 판매자 정보
        SellerEntity sellerEntity = SellerEntity.builder()
                .sellerSeq(0)
                .sellerEmail(registSellerDto.getSellerEmail())
                .sellerNickname(registSellerDto.getSellerNickname())
                .sellerPass(passwordEncoder.encode(pass))
                .build();

        sellerInfoRepository.save(sellerEntity);

        return true;
    }


    // 판매자 로그인
    public ResponseSellerLoginDto login(String sellerEmail) {
        SellerEntity sellerEntity = sellerInfoRepository.findBySellerEmail(sellerEmail);

        if(sellerEntity == null){
            return null;
        }

        long seq = sellerEntity.getSellerSeq();
        TokenDto tokenDto = tokenProvider.createUserToken(sellerEntity.getSellerEmail(), "ROLE_SELLER");

        return new ResponseSellerLoginDto(seq,tokenDto);
    }

    // 판매자 정보 요청
    public SellerDto getSellerInfo(long sellerSeq) {
        // db에서 찾기
        SellerEntity sellerEntity = sellerInfoRepository.findBySellerSeq(sellerSeq);

        if(sellerEntity == null) return null;

        // Entity -> Dto
        SellerDto sellerDto = SellerMapper.MAPPER.toDto(sellerEntity);

        return sellerDto;
    }


    // 판매자 정보 등록
    public boolean setSellerInfo(SellerDto sellerDto) {
        // 판매자 정보가 없으면 false
        if (sellerDto == null) return false;

        // Dto -> Entity
        SellerEntity sellerEntity = SellerMapper.MAPPER.toEntity(sellerDto);

        // 저장하기
        sellerInfoRepository.save(sellerEntity);

        return true;
    }

    // 판매자 정보 수정
    public boolean updateSellerInfo(SellerUpdateDto sellerUpdateDto) {

        // 먼저 SellerSeq로 Seller 정보 가져오기
        SellerEntity sellerEntity = sellerInfoRepository.findBySellerSeq(sellerUpdateDto.getSellerSeq());

        // 판매자 정보가 없으면 false
        if (sellerEntity == null) return false;

        // update 해주기
        sellerEntity.setSellerNickname(sellerUpdateDto.getSellerNickname());
        sellerEntity.setSellerTel(sellerUpdateDto.getSellerTel());
        sellerEntity.setSellerAddress(sellerUpdateDto.getSellerAddress());
        sellerEntity.setSellerInfo(sellerUpdateDto.getSellerInfo());

        // Entity 저장하기
        sellerInfoRepository.save(sellerEntity);

        return true;
    }

    // 판매자 정보 유무 확인
    public boolean checkSellerInfo(long sellerSeq) {
        // db에서 판매자 정보 가져오기
        SellerEntity sellerEntity = sellerInfoRepository.findBySellerSeq(sellerSeq);
        if(sellerEntity == null) return false;

        // 판매자 정보가 있으면 true, 없으면 false
        String sellerInfo = sellerEntity.getSellerInfo();
        
            return true;
    }

}
