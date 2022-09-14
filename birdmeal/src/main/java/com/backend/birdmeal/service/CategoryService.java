package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.CategoryDto;
import com.backend.birdmeal.entity.CategoryEntity;
import com.backend.birdmeal.mapper.CategoryMapper;
import com.backend.birdmeal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategoryList(){
        List<CategoryDto> categoryList = CategoryMapper.MAPPER.toDtoList(categoryRepository.findAll());

        return categoryList;
    }

}
