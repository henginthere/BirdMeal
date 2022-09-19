package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.SaveDonationDto;
import com.backend.birdmeal.dto.UpdateUserDto;
import com.backend.birdmeal.service.DonationService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value="기부 내역 저장",response = Object.class)
    @PostMapping
    public ResponseEntity<?> saveDonation(@RequestBody SaveDonationDto saveDonationDto){
        boolean success = donationService.saveDonation(saveDonationDto);
        ResponseFrame<?> res;

        if(success){
            res = ResponseFrame.of(true,"기부 내역 저장을 성공했습니다.");
        }
        else{
            res = ResponseFrame.of(false,"기부 내역 저장을 실패했습니다.");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
