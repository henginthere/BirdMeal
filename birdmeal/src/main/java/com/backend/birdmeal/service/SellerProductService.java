package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.dto.SellerProductDto;
import com.backend.birdmeal.dto.SellerProductUpdateDto;
import com.backend.birdmeal.entity.CategoryEntity;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.mapper.ProductMapper;
import com.backend.birdmeal.mapper.SellerProductMapper;
import com.backend.birdmeal.repository.CategoryRepository;
import com.backend.birdmeal.repository.SellerInfoRepository;
import com.backend.birdmeal.repository.SellerProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerProductService {

    private final SellerInfoRepository sellerInfoRepository;
    private final SellerProductRepository sellerProductRepository;
    private final CategoryRepository categoryRepository;

    // 상품 판매 등록
    public boolean setSellerProduct(SellerProductDto sellerProductDto) {
        if (sellerProductDto == null) return false;

        // 카테고리 정보가 있는지 확인
        // 없으면 false
        CategoryEntity categoryEntity = categoryRepository.findByCategorySeq(sellerProductDto.getCategorySeq());
        if (categoryEntity == null) return false;

        // 판매자 정보가 있는지 확인
        // 없으면 false
        SellerEntity sellerEntity = sellerInfoRepository.findBySellerSeq(sellerProductDto.getSellerSeq());
        if (sellerEntity == null) return false;

        ProductDto productDto = ProductDto.builder()
                .productSeq(0)
                .categorySeq(sellerProductDto.getCategorySeq())
                .sellerSeq(sellerProductDto.getSellerSeq())
                .productName(sellerProductDto.getProductName())
                .productPrice(sellerProductDto.getProductPrice())
                .productCa(sellerProductDto.getProductCa())
                .productThumbnailImg(sellerProductDto.getProductThumbnailImg())
                .productDescriptionImg(sellerProductDto.getProductDescriptionImg())
                .productIsDeleted(false)
                .build();

        // Dto -> Entity
        ProductEntity productEntity = ProductMapper.MAPPER.toEntity(productDto);

        // 상품 등록 하기
        sellerProductRepository.save(productEntity);

        return true;
    }

    // 상품 정보 수정
    public boolean updateSellerProduct(SellerProductUpdateDto sellerProductUpdateDto) {
        // 상품 불러오기
        ProductEntity productEntity = sellerProductRepository.findByProductSeq(sellerProductUpdateDto.getProductSeq());

        // 상품이 없으면, 번호가 이상하면 false
        if (productEntity == null || productEntity.isProductIsDeleted()) return false;


        productEntity.setProductName(sellerProductUpdateDto.getProductName());
        productEntity.setProductPrice(sellerProductUpdateDto.getProductPrice());
        productEntity.setProductDescriptionImg(sellerProductUpdateDto.getProductDescriptionImg());
        productEntity.setProductThumbnailImg(sellerProductUpdateDto.getProductThumbnailImg());

        // 저장
        sellerProductRepository.save(productEntity);

        return true;
    }

    // 상품 삭제
    public boolean deleteSellerProduct(long productSeq) {
        // 상품 불러오기
        ProductEntity productEntity = sellerProductRepository.findByProductSeq(productSeq);

        // 상품이 없으면 false
        if (productEntity == null) return false;

        // 삭제하기 ( soft delete )
        productEntity.setProductIsDeleted(true);

        // 저장
        sellerProductRepository.save(productEntity);

        return true;
    }

    // 등록 상품 목록 보기
    public List<ProductDto> getSellerProduct(long sellerSeq) {
        // 판매자 번호로 물품 찾기
        List<ProductEntity> productEntityList = sellerProductRepository.findAllBySellerSeqOrderByProductCreateDateDesc(sellerSeq);

        // Entity -> Dto
        List<ProductDto> productDtoList = SellerProductMapper.MAPPER.toDtoList(productEntityList);

        return productDtoList;
    }
}
