package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.DonationDto;
import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.dto.SaveDonationDto;
import com.backend.birdmeal.dto.UpdateUserDto;
import com.backend.birdmeal.entity.DonationEntity;
import com.backend.birdmeal.service.DonationService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("DonationController")
@RequiredArgsConstructor
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationService donationService;

    /**
     * 기부 내역 저장
     *
     * @param saveDonationDto
     * @return Object
     */
    @ApiOperation(value = "기부 내역 저장", response = Object.class)
    @PostMapping
    public ResponseEntity<?> saveDonation(@RequestBody SaveDonationDto saveDonationDto) {
        boolean success = donationService.saveDonation(saveDonationDto);
        ResponseFrame<?> res;

        if (success) {
            res = ResponseFrame.of(true, "기부 내역 저장을 성공했습니다.");
        } else {
            res = ResponseFrame.of(false, "기부 내역 저장을 실패했습니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    /**
     * 전체 기부 내역 불러오기
     *
     * @param
     * @return List
     */
    @ApiOperation(value = "전체 기부 내역 불러오기", response = List.class)
    @GetMapping
    public ResponseEntity<?> getAllDonation() {
        List<DonationDto> donationList = donationService.getAllDonation();
        ResponseFrame<List<DonationDto>> res;
        if (donationList != null) {
            res = ResponseFrame.of(donationList, "카테고리별 상품 목록 요청을 성공했습니다.");
        } else {
            res = ResponseFrame.of(null, "카테고리별 상품 목록 요청을 실패했습니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
