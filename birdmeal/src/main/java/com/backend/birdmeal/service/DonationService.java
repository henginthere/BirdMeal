package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.DonationDto;
import com.backend.birdmeal.dto.SaveDonationDto;
import com.backend.birdmeal.entity.DonationEntity;
import com.backend.birdmeal.mapper.DonationMapper;
import com.backend.birdmeal.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


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
    public List<DonationDto> getAllDonation(){
        List<DonationDto> donationList = DonationMapper.MAPPER.toDtoList(donationRepository.findAll());
        return donationList;
    }

    public List<DonationDto> getMyDonation(long userSeq){
        if(donationRepository.findByUserSeq(userSeq).isPresent()){
            List<DonationDto> donationList = DonationMapper.MAPPER.toDtoList(donationRepository.findByUserSeq(userSeq).get());
            return donationList;
        }
        return null;
    }


}
