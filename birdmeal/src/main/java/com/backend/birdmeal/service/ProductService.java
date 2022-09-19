package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.mapper.ProductMapper;
import com.backend.birdmeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto getProductDetail(Long productSeq){
        ProductDto productDto = ProductMapper.MAPPER.toDto(productRepository.findByProductSeq(productSeq));

        return productDto;
    }
}
