package com.backend.birdmeal.repository;

import com.backend.birdmeal.dto.OrderChildResponseDto;
import com.backend.birdmeal.entity.OrderChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderChildRepository extends JpaRepository<OrderChildEntity, Long> {

    List<OrderChildEntity> findAllByOrderByOrderChildDetailSeqDesc();
}
