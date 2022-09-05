package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.CategoryRequestDto;
import com.alkemy.ong.dto.response.CategoryResponseDto;

public interface ICategoryService {
    
    CategoryResponseDto create(CategoryRequestDto dto);
}
