package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.dto.ProductUpdateDto;
import com.backend.birdmeal.entity.CategoryEntity;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.mapper.SellerProductMapper;
import com.backend.birdmeal.repository.CategoryRepository;
import com.backend.birdmeal.repository.SellerInfoRepository;
import com.backend.birdmeal.repository.SellerProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerProductService {

    private final SellerInfoRepository sellerInfoRepository;
    private final SellerProductRepository sellerProductRepository;
    private final CategoryRepository categoryRepository;

    // 상품 판매 등록
    public boolean setSellerProduct(ProductDto productDto) {
        if (productDto == null) return false;

        // 카테고리 정보가 있는지 확인
        // 없으면 false
        CategoryEntity categoryEntity = categoryRepository.findByCategorySeq(productDto.getCategorySeq());
        if(categoryEntity == null) return false;

        // 판매자 정보가 있는지 확인
        // 없으면 false
        SellerEntity sellerEntity = sellerInfoRepository.findBySellerSeq(productDto.getSellerSeq());
        if(sellerEntity == null) return false;

        // Dto -> Entity
        ProductEntity productEntity = SellerProductMapper.MAPPER.toEntity(productDto);

        // 상품 등록 하기
        sellerProductRepository.save(productEntity);

        return true;
    }

    // 상품 정보 수정
    public boolean updateSellerProduct(ProductUpdateDto productUpdateDto){
        // 상품 불러오기
        ProductEntity productEntity = sellerProductRepository.findByProductSeq(productUpdateDto.getProductSeq());
        
        // 상품이 없으면 false
        if(productEntity == null) return false;

        // 수정하기
        productEntity.setProductPrice(productUpdateDto.getProductPrice());
        productEntity.setProductThumbnailImg(productUpdateDto.getProductThumbnailImg());
        productEntity.setProductDescriptionImg(productUpdateDto.getProductDescriptionImg());

        // 저장
        sellerProductRepository.save(productEntity);

        return true;
    }

    // 상품 삭제
    public boolean deleteSellerProduct(long productSeq){
        // 상품 불러오기
        ProductEntity productEntity = sellerProductRepository.findByProductSeq(productSeq);

        // 상품이 없으면 false
        if(productEntity == null) return false;

        // 삭제하기 ( soft delete )
        productEntity.setProductIsDeleted(true);

        // 저장
        sellerProductRepository.save(productEntity);

        return true;
    }
}
