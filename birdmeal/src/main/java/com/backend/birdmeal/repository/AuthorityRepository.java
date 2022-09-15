package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {

}
