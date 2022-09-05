package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.request.TestimonialRequestDto;
import com.alkemy.ong.dto.response.TestimonialResponseDto;
import com.alkemy.ong.exception.UnableToSaveEntityException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.ITestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

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
}
