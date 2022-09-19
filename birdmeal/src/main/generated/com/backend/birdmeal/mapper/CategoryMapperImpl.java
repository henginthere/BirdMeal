package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.CategoryDto;
import com.backend.birdmeal.dto.CategoryDto.CategoryDtoBuilder;
import com.backend.birdmeal.entity.CategoryEntity;
import com.backend.birdmeal.entity.CategoryEntity.CategoryEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-20T00:24:38+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryEntity toEntity(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.categorySeq( categoryDto.getCategorySeq() );
        categoryEntity.categoryName( categoryDto.getCategoryName() );
        categoryEntity.categoryIcon( categoryDto.getCategoryIcon() );

        return categoryEntity.build();
    }

    @Override
    public CategoryDto toDto(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.categorySeq( categoryEntity.getCategorySeq() );
        categoryDto.categoryName( categoryEntity.getCategoryName() );
        categoryDto.categoryIcon( categoryEntity.getCategoryIcon() );

        return categoryDto.build();
    }

    @Override
    public List<CategoryDto> toDtoList(List<CategoryEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( entityList.size() );
        for ( CategoryEntity categoryEntity : entityList ) {
            list.add( toDto( categoryEntity ) );
        }

        return list;
    }

    @Override
    public List<CategoryEntity> toEntityList(List<CategoryDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CategoryEntity> list = new ArrayList<CategoryEntity>( dtoList.size() );
        for ( CategoryDto categoryDto : dtoList ) {
            list.add( toEntity( categoryDto ) );
        }

        return list;
    }
}
