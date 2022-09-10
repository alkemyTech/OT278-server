package com.alkemy.ong.service;

import com.alkemy.ong.dto.slide.SlideBasicResponseDto;
import com.alkemy.ong.dto.slide.SlideRequestDto;
import com.alkemy.ong.dto.slide.SlideResponseDTO;
import com.alkemy.ong.dto.slide.SlideResponseDto;

import java.util.List;

public interface ISlideService {

    SlideResponseDto create(SlideRequestDto dto);

    List<SlideBasicResponseDto> getAll();

    void delete(Long id);
    SlideResponseDto update(SlideRequestDto dto, Long id);

}
