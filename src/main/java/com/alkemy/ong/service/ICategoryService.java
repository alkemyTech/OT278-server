package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.CategoryRequestDto;
import com.alkemy.ong.dto.response.CategoryResponseDto;

import com.alkemy.ong.dto.category.CategoryNameDto;

import java.util.List;

public interface ICategoryService {
    
    CategoryResponseDto create(CategoryRequestDto dto);

    List<CategoryNameDto> getAll();

}
