package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.dto.ProductResponseDto;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.repository.ProductRepository;
import com.backend.birdmeal.repository.SellerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerInfoRepository sellerInfoRepository;

    public ProductResponseDto getProductDetail(Long productSeq){
        ProductEntity productEntity = productRepository.findByProductSeq(productSeq);

        SellerEntity sellerEntity = sellerInfoRepository.findBySellerSeq(productEntity.getSellerSeq());

        ProductResponseDto productDto = ProductResponseDto.builder()
                .productSeq(productEntity.getProductSeq())
                .categorySeq(productEntity.getCategorySeq())
                .sellerSeq(productEntity.getSellerSeq())
                .sellerName(sellerEntity.getSellerNickname())
                .productName(productEntity.getProductName())
                .productPrice(productEntity.getProductPrice())
                .productCa(productEntity.getProductCa())
                .productThumbnailImg(productEntity.getProductThumbnailImg())
                .productDescriptionImg(productEntity.getProductDescriptionImg())
                .productIsDeleted(productEntity.isProductIsDeleted())
                .productCreateDate(productEntity.getProductCreateDate())
                .productUpdateDate(productEntity.getProductUpdateDate())
                .build();

        return productDto;
    }
}
