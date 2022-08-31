package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.ITestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestimonialServiceImpl implements ITestimonialService {

    private final TestimonialRepository repository;
    private final TestimonialMapper mapper;

    public TestimonialDto save(TestimonialDto dto) {
        Testimonial entity = mapper.testimonialDto2TestimonialEntity(dto);
        Testimonial savedEntity = repository.save(entity);
        return mapper.testimonialEntity2testimonialDto(savedEntity);
    }
}
