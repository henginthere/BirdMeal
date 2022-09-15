package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerProductRepository extends JpaRepository<ProductEntity,Long> {
}
