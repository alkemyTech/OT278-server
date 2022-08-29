package com.alkemy.ong.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;
import com.alkemy.ong.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @RolesAllowed(value = "ADMIN")
    @RequestMapping(value = "/new", method = POST, consumes = {"multipart/form-data"})
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestParam String dto,
                                                      @RequestPart(required = false) MultipartFile image) 
                                                      throws JsonMappingException, JsonProcessingException {

        CategoryRequestDto requestDto  = new ObjectMapper().readValue(dto, CategoryRequestDto.class);

        return ResponseEntity.status(CREATED)
                             .body(categoryServiceImpl.create(requestDto, image));
    }

}
