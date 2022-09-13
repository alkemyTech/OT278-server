package com.alkemy.ong.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.alkemy.ong.dto.category.CategoryNameDto;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.exception.NotFoundException;

import com.alkemy.ong.exception.*;
import com.alkemy.ong.mapper.GenericMapper;
import com.alkemy.ong.service.ICategoryService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.category.CategoryRequestDto;
import com.alkemy.ong.dto.category.CategoryResponseDto;

import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {


    private final CategoryRepository repository;
    private final MessageSource messageSource;
    private final GenericMapper mapper;

    @Override
    public CategoryResponseDto create(CategoryRequestDto dto) {
        Category categorySaved;
        try {
            List<Category> categories = repository.findAll();

            categories.forEach(c -> {
                if (c.getName().equalsIgnoreCase(dto.getName())) {
                    throw new AlreadyExistsException(
                            messageSource.getMessage("already-exists", new Object[] { "Category name" }, Locale.US));
                }
            });

            Category category = mapper.map(dto, Category.class);
            category.setCreationDate(LocalDateTime.now());
            category.setUpdateDate(LocalDateTime.now());

            /*
             * TODO: <- ImageService should validate and return the path of the File...
             * example:
             * category.setImage(imageService.getImage(dto.getImage()));
             */
            category.setImage(dto.getImage());

            categorySaved = repository.save(category);
        } catch (Exception e) {
            throw new UnableToSaveEntityException(
                    messageSource.getMessage("unable-to-save-entity", new Object[] { "the new Category: " }, Locale.US)
                    + e.getMessage());
        }

        return mapper.map(categorySaved, CategoryResponseDto.class);
    }

    public List<CategoryNameDto> getAll() {
        List<Category> entities = repository.findAll();
        if (entities.isEmpty())
            throw new EmptyListException(messageSource.getMessage("empty-list", null, Locale.US));
        return mapper.mapAll(entities, CategoryNameDto.class);
    }

    public CategoryResponseDto getById(Long id) {
        if (id <= 0) {
            throw new ArithmeticException(messageSource.getMessage("error-negative-id", null, Locale.US));
        }
        Category entity = getCategoryById(id);
        return mapper.map(entity, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto dto) {
        Category entity = getCategoryById(id);
        try{
            Category updatedEntity = mapper.map(dto, Category.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setCreationDate(entity.getCreationDate());
            updatedEntity.setUpdateDate(LocalDateTime.now());
            repository.save(updatedEntity);
            return mapper.map(updatedEntity, CategoryResponseDto.class);
        }catch (Exception e){
            throw new UnableToUpdateEntityException(messageSource.getMessage("unable-to-update-entity",new Object[]{"Category"},Locale.US));
        }
    }

    public void delete(Long id) {
        Category entity = getCategoryById(id);
        try {
            entity.setUpdateDate(LocalDateTime.now());
            repository.deleteById(id);
        } catch (Exception e) {
            throw new UnableToDeleteEntityException(messageSource.getMessage("unable-to-delete-entity", new Object[] { id }, Locale.US));
        }
    }

    private Category getCategoryById(Long id) {
        Optional<Category> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found",new Object[] { "Category" } ,Locale.US));
        return entity.get();
    }

}
