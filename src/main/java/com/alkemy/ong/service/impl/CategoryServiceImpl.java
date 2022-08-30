package com.alkemy.ong.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;
import com.alkemy.ong.exception.AlreadyExistsException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;
    private final CategoryMapper mapper;

    @Override
    public CategoryResponseDto create(CategoryRequestDto dto, MultipartFile image) {
        List<Category> categories = categoryRepository.findAll();
        
        categories.forEach(c -> {
            if(c.getName().equalsIgnoreCase(dto.getName())){
                throw new AlreadyExistsException(messageSource.getMessage("category.already-exists", null, Locale.US));
            }});

        Category category = mapper.categoryDto2CategoryEntity(dto);

        category.setCreationTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        category.setUpdateTimeStamp(Timestamp.valueOf(LocalDateTime.now()));

        /*
            TODO: <- ImageService should validate and return the path of the File...
            category.setImage(imageService.getImage(image));
        */
        category.setImage("image-example.png");    
        
        Category categorySaved = categoryRepository.save(category);
       
        return mapper.CategoryEntity2CategoryDto(categorySaved);
    }
    
}
