package com.backend.birdmeal.repository;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductSeq(long productSeq);

    List<ProductEntity> findAllByCategorySeq(Long categorySeq);
}
