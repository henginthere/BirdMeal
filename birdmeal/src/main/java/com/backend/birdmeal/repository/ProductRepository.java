package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductSeq(long productSeq);
}