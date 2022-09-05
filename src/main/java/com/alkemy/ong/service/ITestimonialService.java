package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.TestimonialRequestDto;
import com.alkemy.ong.dto.response.TestimonialResponseDto;

public interface ITestimonialService {

    TestimonialResponseDto save(TestimonialRequestDto dto);
}
