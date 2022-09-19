package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.CategoryDto;
import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.mapper.CategoryMapper;
import com.backend.birdmeal.mapper.ProductMapper;
import com.backend.birdmeal.repository.CategoryRepository;
import com.backend.birdmeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;

    public List<CategoryDto> getCategoryList(){
        List<CategoryDto> categoryList = CategoryMapper.MAPPER.toDtoList(categoryRepository.findAll());

        return categoryList;
    }

    public List<ProductDto> getProductList(Long categorySeq){
        List<ProductDto> productList = ProductMapper.MAPPER.toDtoList(productRepository.findByCategorySeq(categorySeq));

        return productList;
    }

}
