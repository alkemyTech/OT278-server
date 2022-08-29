package com.alkemy.ong.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public CategoryResponseDto create(CategoryRequestDto dto, MultipartFile image) {
        Category category = mapper.categoryDto2CategoryEntity(dto);

        /*
            TODO: <- ImageService should validate and return the path of the File...
            category.setImage(imageService.getImage(image));
        */
        category.setImage("image-example.png");    
        
        Category categorySaved = categoryRepository.save(category);
       
        return mapper.CategoryEntity2CategoryDto(categorySaved);
    }
    
}
