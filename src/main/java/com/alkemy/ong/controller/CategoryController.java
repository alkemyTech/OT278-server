package com.alkemy.ong.controller;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

import com.alkemy.ong.dto.category.CategoryNameDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;
import com.alkemy.ong.service.ICategoryService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> addNewCategory(@RequestBody @Valid CategoryRequestDto dto){        

        return ResponseEntity.status(CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryNameDto>> getAll() {
        List<CategoryNameDto> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id,@RequestBody @Valid CategoryRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }
}
