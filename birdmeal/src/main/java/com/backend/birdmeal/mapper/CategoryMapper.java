package com.backend.birdmeal.mapper;

import com.backend.birdmeal.dto.CategoryDto;
import com.backend.birdmeal.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper extends StructMapper<CategoryDto, CategoryEntity> {

    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    @Override
    CategoryEntity toEntity(final CategoryDto categoryDto);
    @Override
    CategoryDto toDto(final CategoryEntity categoryEntity);

    @Override
    List<CategoryDto> toDtoList(List<CategoryEntity> entityList);

    @Override
    List<CategoryEntity> toEntityList(List<CategoryDto> dtoList);

}
