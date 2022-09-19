package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.SaveDonationDto;
import com.backend.birdmeal.entity.DonationEntity;
import com.backend.birdmeal.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    public boolean saveDonation(SaveDonationDto saveDonationDto){

        boolean donationType;
        if(saveDonationDto.isDonationType()){
            //donationType이 true이면 간접기부
            donationType=true;
        }
        else{
            donationType=false;
        }
        DonationEntity donationEntity = DonationEntity.builder()
                .donationSeq(0)
                .userSeq(saveDonationDto.getUserSeq())
                .donationPrice(saveDonationDto.getDonationPrice())
                .donationType(donationType)
                .build();


        donationRepository.save(donationEntity);
        return true;
    }

}
