package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.mapper.SellerProductMapper;
import com.backend.birdmeal.repository.SellerProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerProductService {

    private final SellerProductRepository sellerProductRepository;

    // 상품 판매 등록
    public boolean setSellerProduct(ProductDto productDto) {
        if (productDto == null) return false;

        // Dto -> Entity
        ProductEntity productEntity = SellerProductMapper.MAPPER.toEntity(productDto);

        // 상품 등록 하기
        sellerProductRepository.save(productEntity);

        return true;
    }
}
