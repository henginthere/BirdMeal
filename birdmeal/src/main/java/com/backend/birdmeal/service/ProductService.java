package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ProductResponseDto;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.repository.ProductRepository;
import com.backend.birdmeal.repository.SellerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    // 상품 검색하기
    public List<ProductResponseDto> searchProductInfo(String productSearchName) {
        List<ProductResponseDto> resList = new ArrayList<>();

        List<ProductEntity> list = productRepository.findAllByProductNameContaining(productSearchName);

        for(int i=0; i<list.size();i++){
            ProductEntity productEntity = list.get(i);

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

            resList.add(productDto);
        }
    return resList;
    }
}
