package com.alkemy.ong.mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;
import com.alkemy.ong.model.Category;

@Component
public class CategoryMapper {
 
    public Category categoryDto2CategoryEntity(CategoryRequestDto dto) {
        Category entity = new Category();
        
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreationTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdateTimeStamp(Timestamp.valueOf(LocalDateTime.now()));

        return entity;
    }

    public CategoryResponseDto CategoryEntity2CategoryDto(Category entity) {        
       CategoryResponseDto dto = new CategoryResponseDto();

       dto.setId(entity.getId());
       dto.setName(entity.getName());
       dto.setDescription(entity.getDescription());
       dto.setCreationTimestamp(entity.getCreationTimestamp());
       dto.setUpdateTimeStamp(entity.getUpdateTimeStamp());
       dto.setDeleted(entity.isDeleted());
       dto.setImage(entity.getImage());

       return dto;
    }
}
