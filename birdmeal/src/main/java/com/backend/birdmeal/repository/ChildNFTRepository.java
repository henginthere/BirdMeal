package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.ChildNFTEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildNFTRepository extends JpaRepository<ChildNFTEntity, Long> {
    ChildNFTEntity findByNftSeq(long num);
}
