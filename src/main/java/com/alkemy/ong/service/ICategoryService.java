package com.alkemy.ong.service;

import org.springframework.web.multipart.MultipartFile;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;

public interface ICategoryService {
    
    CategoryResponseDto create(CategoryRequestDto dto, MultipartFile image);
}
