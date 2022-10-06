package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.StarvingChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StarvingChildRepository extends JpaRepository<StarvingChildEntity, Long> {

    Optional<StarvingChildEntity> findByChildCardNum(String cardNum);

}
