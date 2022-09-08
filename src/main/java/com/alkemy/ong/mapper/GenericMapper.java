package com.alkemy.ong.mapper;

import java.io.Serializable;
import java.util.List;
import static java.util.stream.Collectors.toList;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.slide.SlideResponseDto;
import com.alkemy.ong.model.Slide;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenericMapper implements Serializable {

    private final ModelMapper mapper;

    public <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    public <S, D> List<D> mapAll(List<S> sourceList, Class<D> destinationClass) {
        return sourceList.stream()
                .map(e -> map(e, destinationClass))
                .collect(toList());
    }

    public SlideResponseDto mapSlideToResponseDto(Slide slide){
        return mapper.typeMap(Slide.class, SlideResponseDto.class)
                     .addMapping(s -> s.getOrganizationId(), SlideResponseDto::setOrganizationId)
                     .map(slide);
    }
}