package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.ChildNFTRequestDto;
import com.backend.birdmeal.dto.ChildNFTResponseDto;
import com.backend.birdmeal.dto.NFTImgResponseDto;
import com.backend.birdmeal.service.NFTService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("NFTController")
@RequiredArgsConstructor
@RequestMapping("/api/nft")
public class NFTController {

    private final NFTService nftService;

    /**
     * NFT ImgURI 저장
     *
     * @param childNFTRequestDto
     * @return Object
     */

    @ApiOperation(value="NFT ImgURI 저장",response = Object.class)
    @PostMapping("/image")
    public ResponseEntity<?> setNFTImg(@RequestBody ChildNFTRequestDto childNFTRequestDto){
        boolean success = nftService.setNFTImg(childNFTRequestDto);

        ResponseFrame<?> res;
        if(success) {
            res = ResponseFrame.of(success, "NFT ImgURI 저장을 성공했습니다.");
        }else{
            res = ResponseFrame.of(success, "아이들 정보가 없거나 이미지가 없어 NFT ImgURI 저장을 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * NFT imgURI 가져오기
     *
     * @param userSeq
     * @return Object
     */

    @ApiOperation(value="NFT imgURI 가져오기",response = Object.class)
    @GetMapping("/{user-seq}")
    public ResponseEntity<?> getNFTImg(@PathVariable("user-seq") long userSeq){
        NFTImgResponseDto nftImgResponseDto = nftService.getNFTImg(userSeq);

        ResponseFrame<?> res;
        if(nftImgResponseDto == null) {
            res = ResponseFrame.of(false, "NFT imgURI가 없어서 가져오기를 실패했습니다.");
        }else{
            res = ResponseFrame.of(nftImgResponseDto, "NFT imgURI 가져오기를 성공했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     * 내가 만든 포토카드 요청 ( 아이들 )
     *
     * @param userSeq
     * @return Object
     */

    @ApiOperation(value="내가 만든 포토카드 요청 ( 아이들 )",response = Object.class)
    @GetMapping("/user/child/{user-seq}")
    public ResponseEntity<?> getMyCardInfo(@PathVariable("user-seq") long userSeq){
        List<ChildNFTResponseDto> list = nftService.getMyCardInfo(userSeq);

        ResponseFrame<?> res;
        if(list.size()==0) {
            res = ResponseFrame.of(false, "내가 만든 포토카드 요청 ( 아이들 ) 을 실패했습니다.");
        }else{
            res = ResponseFrame.of(list, "내가 만든 포토카드 요청 ( 아이들 ) 을 성공했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 사용자 IsMint 상태 바꾸기
     *
     * @param userSeq
     * @return Object
     */

    @ApiOperation(value="사용자 IsMint 상태 바꾸기",response = Object.class)
    @PutMapping("/mint-state/{user-seq}")
    public ResponseEntity<?> changeState(@PathVariable("user-seq") long userSeq){
        boolean success = nftService.changeState(userSeq);

        ResponseFrame<?> res;
        if(success) {
            res = ResponseFrame.of(success, "사용자 IsMint 상태 바꾸기를 성공했습니다.");
        }else{
            res = ResponseFrame.of(success, "10만원을 넘지 않아 사용자 IsMint 상태 바꾸기를 실패했습니다.");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
