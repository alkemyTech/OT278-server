package com.alkemy.ong.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;
import com.alkemy.ong.service.impl.CategoryServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @RolesAllowed("ADMIN")
    @PostMapping("/new")
    public ResponseEntity<CategoryResponseDto> addNewCategory(@Valid @ModelAttribute CategoryRequestDto dto,
                                                        @RequestPart(required = false) MultipartFile image){        

        return ResponseEntity.status(CREATED).body(categoryServiceImpl.create(dto, image));
    }
}
