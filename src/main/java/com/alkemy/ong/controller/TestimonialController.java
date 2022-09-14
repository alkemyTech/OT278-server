package com.alkemy.ong.controller;

import com.alkemy.ong.dto.testimonial.TestimonialRequestDto;
import com.alkemy.ong.dto.testimonial.TestimonialResponseDto;
import com.alkemy.ong.exception.CustomExceptionDetails;
import com.alkemy.ong.service.ITestimonialService;
import com.alkemy.ong.utils.messenger.IMessenger;

import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;

import static org.apache.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/testimonials")
@RequiredArgsConstructor
@RestController
public class TestimonialController {

    private final ITestimonialService service;

    /*
     * @ApiOperation(value = "New Testimony", notes =
     * "As an admin user you can save a new testimonial")
     * 
     * @ApiResponses(value = {
     * 
     * @ApiResponse(code = SC_CREATED,
     * message = "CREATED - Resource is fetched successfully",
     * response = TestimonialResponseDto.class),
     * 
     * @ApiResponse(code = SC_INTERNAL_SERVER_ERROR,
     * message = "Unable to save entity in the database.",
     * response = CustomExceptionDetails.class)
     * })
     */

    @Operation(summary = "Save a new Testimony", description = "As an admin user you can save a new testimonial, this controller return a new testimonial entity", 
        responses = {
            @ApiResponse(responseCode = "201", description = "CREATED - Resource is fetched successfully", 
                content = @Content(schema = @Schema(implementation = TestimonialResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unable to save entity in the database", 
                content = @Content(schema = @Schema(implementation = CustomExceptionDetails.class), examples = @ExampleObject)) 
        })

    /*
     * @Operation(description = "As an admin user you can save a new testimonial")
     * 
     * @ApiResponses(value = {
     * 
     * @ApiResponse(responseCode = "201", description =
     * "CREATED - Resource is fetched successfully", content = {
     * 
     * @Content(mediaType = "application/json", schema = @Schema(implementation =
     * TestimonialResponseDto.class))
     * }),
     * 
     * @ApiResponse(responseCode = "500", description =
     * "Unable to save entity in the database.", content = {
     * 
     * @Content(mediaType = "application/json", schema = @Schema(implementation =
     * CustomExceptionDetails.class))
     * })
     * })
     */
    @PostMapping
    public ResponseEntity<TestimonialResponseDto> save(@Valid @RequestBody TestimonialRequestDto testimonial) {
        TestimonialResponseDto savedTestimonial = service.save(testimonial);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTestimonial);
    }

    @PutMapping("/:{id}")
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
