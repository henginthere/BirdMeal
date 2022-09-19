package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerEntity, Long> {

    SellerEntity findBySellerSeq(long sellerSeq);


    SellerEntity findBySellerEmail(String sellerEmail);
}
