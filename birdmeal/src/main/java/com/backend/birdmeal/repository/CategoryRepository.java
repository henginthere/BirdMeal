package com.backend.birdmeal.repository;

import com.backend.birdmeal.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAll();
    CategoryEntity findByCategorySeq(long categorySeq);
}
