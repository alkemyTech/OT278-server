package com.alkemy.ong.controller;

import com.alkemy.ong.dto.testimonial.TestimonialRequestDto;
import com.alkemy.ong.dto.testimonial.TestimonialResponseDto;
import com.alkemy.ong.service.ITestimonialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/testimonials")
@RequiredArgsConstructor
@RestController
@Api(value = "Testimonial controller", description = "This controller has a CRUD for testimonials")
public class TestimonialController {

    private final ITestimonialService service;

    @ApiOperation(value = "Save a new Testimony", notes = "As an admin user, you can save a new testimonial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED - Resource is fetched successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TestimonialResponseDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN - User not logged / User logged whitout ROLE_ADMIN", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "500", description = "INTERNAL ERROR - Unable to save entity in the database.", content = {
                    @Content(mediaType = "application/json")
            })
    })
    @PostMapping
    public ResponseEntity<TestimonialResponseDto> save(
            @Valid @RequestBody @Parameter(description = "Request DTO for create a new Testimonial") TestimonialRequestDto testimonial) {
        TestimonialResponseDto savedTestimonial = service.save(testimonial);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTestimonial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponseDto> update(@Valid @RequestBody TestimonialRequestDto newTestimonial,
            @PathVariable Long id) {
        TestimonialResponseDto updatedTestimonial = service.update(newTestimonial, id);
        return ResponseEntity.ok(updatedTestimonial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
