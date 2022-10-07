package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    List<OrderDetailEntity> findAllByOrderSeq(long orderSeq);

    OrderDetailEntity findByOrderDetailSeq(Long orderDetailSeq);

    List<OrderDetailEntity> findAllBySellerSeq(long sellerSeq);

    List<OrderDetailEntity> findAllByOrderDetailSeq(long orderDetailSeq);


    List<OrderDetailEntity> findAllByProductSeq(long productSeq);

    void deleteByOrderDetailSeq(long orderDetailSeq);

    List<OrderDetailEntity> findAllByOrderSeqOrderByOrderDetailSeqDesc(long orderSeq);
}
