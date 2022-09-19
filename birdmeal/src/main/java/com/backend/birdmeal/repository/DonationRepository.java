package com.backend.birdmeal.repository;

import com.backend.birdmeal.dto.DonationDto;
import com.backend.birdmeal.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<DonationEntity, Long> {

    List<DonationEntity> findAll();
}
