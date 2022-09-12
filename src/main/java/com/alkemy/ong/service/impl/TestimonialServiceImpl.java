package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.testimonial.TestimonialRequestDto;
import com.alkemy.ong.dto.testimonial.TestimonialResponseDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.UnableToDeleteEntityException;
import com.alkemy.ong.exception.UnableToSaveEntityException;
import com.alkemy.ong.exception.UnableToUpdateEntityException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.service.ITestimonialService;
import com.alkemy.ong.repository.TestimonialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TestimonialServiceImpl implements ITestimonialService {

    private final TestimonialRepository repository;
    private final TestimonialMapper mapper;
    private final MessageSource messageSource;

    public TestimonialResponseDto save(TestimonialRequestDto dto) {
        try {
            Testimonial entity = mapper.testimonialDto2TestimonialEntity(dto);
            Testimonial savedEntity = repository.save(entity);
            return mapper.testimonialEntity2testimonialDto(savedEntity);
        } catch (Exception e){
            throw new UnableToSaveEntityException(messageSource.getMessage("unable-to-save-entity", null, Locale.US));
        }
    }

    public TestimonialResponseDto update(TestimonialRequestDto dto, Long id) {
        Testimonial entity = getTestimonialById(id);
        try {
            entity.setName(dto.getName());
            // TODO: Implement image service
            entity.setImage(dto.getImage());
            entity.setContent(dto.getContent());
            entity.setUpdateDate(LocalDateTime.now());
            repository.save(entity);
            return mapper.testimonialEntity2testimonialDto(entity);
        } catch (Exception e) {
            throw new UnableToUpdateEntityException(messageSource.getMessage("unable-to-update-entity", new Object[] { "Testimonial" }, Locale.US));
        }
    }

    private Testimonial getTestimonialById(Long id) {
        Optional<Testimonial> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found",new Object[] { "Entity with Id: " + id } ,Locale.US));
        return entity.get();
    }

    public void delete(Long id){
        Testimonial entity = getTestimonialById(id);
        try {
            entity.setUpdateDate(LocalDateTime.now());
            repository.save(entity);
            repository.deleteById(id);
        }catch (Exception e) {
            throw new UnableToDeleteEntityException(messageSource.getMessage("unable-to-delete-entity", new Object[] { id }, Locale.US));
        }
    }

}
