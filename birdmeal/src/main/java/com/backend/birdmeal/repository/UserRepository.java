package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserEmail(String email);

    Optional<UserEntity> findByUserSeq(Long seq);

}
