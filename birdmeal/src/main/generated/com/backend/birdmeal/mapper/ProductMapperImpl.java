package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.dto.ProductDto.ProductDtoBuilder;
import com.backend.birdmeal.entity.ProductEntity;
import com.backend.birdmeal.entity.ProductEntity.ProductEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-16T00:01:20+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public List<ProductDto> toDtoList(List<ProductEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( entityList.size() );
        for ( ProductEntity productEntity : entityList ) {
            list.add( toDto( productEntity ) );
        }

        return list;
    }

    @Override
    public List<ProductEntity> toEntityList(List<ProductDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ProductEntity> list = new ArrayList<ProductEntity>( dtoList.size() );
        for ( ProductDto productDto : dtoList ) {
            list.add( toEntity( productDto ) );
        }

        return list;
    }

    @Override
    public ProductEntity toEntity(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductEntityBuilder productEntity = ProductEntity.builder();

        productEntity.productSeq( productDto.getProductSeq() );
        productEntity.categorySeq( productDto.getCategorySeq() );
        productEntity.sellerSeq( productDto.getSellerSeq() );
        productEntity.productName( productDto.getProductName() );
        productEntity.productPrice( productDto.getProductPrice() );
        productEntity.productCa( productDto.getProductCa() );
        productEntity.productThumbnailImg( productDto.getProductThumbnailImg() );
        productEntity.productDescriptionImg( productDto.getProductDescriptionImg() );
        productEntity.productIsDeleted( productDto.isProductIsDeleted() );
        productEntity.productCreateDate( productDto.getProductCreateDate() );
        productEntity.productUpdateDate( productDto.getProductUpdateDate() );

        return productEntity.build();
    }

    @Override
    public ProductDto toDto(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductDtoBuilder productDto = ProductDto.builder();

        productDto.productSeq( productEntity.getProductSeq() );
        productDto.categorySeq( productEntity.getCategorySeq() );
        productDto.sellerSeq( productEntity.getSellerSeq() );
        productDto.productName( productEntity.getProductName() );
        productDto.productPrice( productEntity.getProductPrice() );
        productDto.productCa( productEntity.getProductCa() );
        productDto.productThumbnailImg( productEntity.getProductThumbnailImg() );
        productDto.productDescriptionImg( productEntity.getProductDescriptionImg() );
        productDto.productIsDeleted( productEntity.isProductIsDeleted() );
        productDto.productCreateDate( productEntity.getProductCreateDate() );
        productDto.productUpdateDate( productEntity.getProductUpdateDate() );

        return productDto.build();
    }
}
