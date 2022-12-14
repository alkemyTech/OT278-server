package com.alkemy.ong.service;

import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.dto.category.CategoryNameDto;
import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;
import java.util.List;

public interface ICategoryService {
    
    CategoryResponseDto create(CategoryRequestDto dto);

    List<CategoryNameDto> getAll();

    CategoryResponseDto getById(Long id);

    CategoryResponseDto update(Long id,CategoryRequestDto dto);
    void delete(Long id);

    PageDto<CategoryResponseDto> getPage(int pageNum);
}
