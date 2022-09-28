package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByOrderSeq(Long orderSeq);

    List<OrderEntity> findAllByUserSeqOrderByOrderSeqDesc(long userSeq);
}
