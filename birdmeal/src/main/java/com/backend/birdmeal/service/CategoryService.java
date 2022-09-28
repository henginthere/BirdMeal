package com.backend.birdmeal.service;

import com.backend.birdmeal.dto.CategoryDto;
import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.dto.ProductResponseDto;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.entity.SellerEntity;
import com.backend.birdmeal.mapper.CategoryMapper;
import com.backend.birdmeal.mapper.ProductMapper;
import com.backend.birdmeal.repository.CategoryRepository;
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
public class CategoryService {

    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;
    final SellerInfoRepository sellerInfoRepository;

    public List<CategoryDto> getCategoryList(){
        List<CategoryDto> categoryList = CategoryMapper.MAPPER.toDtoList(categoryRepository.findAll());

        return categoryList;
    }

    public List<ProductResponseDto> getProductList(Long categorySeq){
        List<ProductEntity> list = productRepository.findAllByCategorySeq(categorySeq);

        List<ProductResponseDto> resList = new ArrayList<>();

        for(int i=0; i<list.size(); i++){
            SellerEntity sellerEntity = sellerInfoRepository.findBySellerSeq(list.get(i).getSellerSeq());

            ProductResponseDto productDto = ProductResponseDto.builder()
                    .productSeq(list.get(i).getProductSeq())
                    .categorySeq(list.get(i).getCategorySeq())
                    .sellerSeq(list.get(i).getSellerSeq())
                    .sellerName(sellerEntity.getSellerNickname())
                    .productName(list.get(i).getProductName())
                    .productPrice(list.get(i).getProductPrice())
                    .productCa(list.get(i).getProductCa())
                    .productThumbnailImg(list.get(i).getProductThumbnailImg())
                    .productDescriptionImg(list.get(i).getProductDescriptionImg())
                    .productIsDeleted(list.get(i).isProductIsDeleted())
                    .productCreateDate(list.get(i).getProductCreateDate())
                    .productUpdateDate(list.get(i).getProductUpdateDate())
                    .build();

            resList.add(productDto);
        }
        return resList;
    }
}
