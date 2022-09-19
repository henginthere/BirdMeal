package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
}
