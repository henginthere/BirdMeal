package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserEmail(String email);
}
