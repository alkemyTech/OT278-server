package com.alkemy.ong.mapper;

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.request.CategoryRequestDto;
import com.alkemy.ong.dto.response.CategoryResponseDto;
import com.alkemy.ong.model.Category;

@Component
public class CategoryMapper {
 
    public Category categoryDto2CategoryEntity(CategoryRequestDto dto) {
        Category entity = new Category();
        
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public CategoryResponseDto CategoryEntity2CategoryDto(Category entity) {        
       CategoryResponseDto dto = new CategoryResponseDto();

       dto.setId(entity.getId());
       dto.setName(entity.getName());
       dto.setDescription(entity.getDescription());
       dto.setImage(entity.getImage());

       return dto;
    }
}
