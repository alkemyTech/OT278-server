package com.alkemy.ong.controller;

import com.alkemy.ong.dto.testimonial.TestimonialRequestDto;
import com.alkemy.ong.dto.testimonial.TestimonialResponseDto;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.ITestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/testimonials")
@RequiredArgsConstructor
@RestController
public class TestimonialController {

    private final ITestimonialService service;
    private final TestimonialRepository testimonialRepository;

    @PostMapping
    public ResponseEntity<TestimonialResponseDto> save(@Valid @RequestBody TestimonialRequestDto testimonial) {
        TestimonialResponseDto savedTestimonial = service.save(testimonial);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTestimonial);
    }

    @PutMapping("/:{id}")
    public ResponseEntity<TestimonialResponseDto> update(@Valid @RequestBody TestimonialRequestDto newTestimonial, @PathVariable Long id) {
        TestimonialResponseDto updatedTestimonial = service.update(newTestimonial, id);
        return ResponseEntity.ok(updatedTestimonial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public Page<Testimonial> getAll(@PageableDefault(page=0, size = 10)Pageable pageable) {
        return testimonialRepository.findAll(pageable);
    }
}
