package com.alkemy.ong.controller;

import com.alkemy.ong.dto.request.TestimonialRequestDto;
import com.alkemy.ong.service.ITestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/testimonials")
@RequiredArgsConstructor
@RestController
public class TestimonialController {

    private final ITestimonialService service;

    @PostMapping
    public ResponseEntity<TestimonialRequestDto> save(@Valid @RequestBody TestimonialRequestDto testimonial) {
        TestimonialRequestDto savedTestimonial = service.save(testimonial);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTestimonial);
    }
}
