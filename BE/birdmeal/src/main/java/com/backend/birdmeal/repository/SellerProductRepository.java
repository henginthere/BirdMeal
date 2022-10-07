package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerProductRepository extends JpaRepository<ProductEntity,Long> {
    ProductEntity findByProductSeq(long productSeq);
    List<ProductEntity> findAllBySellerSeqOrderByProductSeqDesc(long sellerSeq);
}