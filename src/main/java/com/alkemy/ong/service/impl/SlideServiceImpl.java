package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.slide.SlideBasicResponseDto;
import com.alkemy.ong.dto.slide.SlideRequestDto;
import com.alkemy.ong.dto.slide.SlideResponseDto;
import com.alkemy.ong.dto.testimonial.TestimonialRequestDto;
import com.alkemy.ong.dto.testimonial.TestimonialResponseDto;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.UnableToUpdateEntityException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.service.ISlideService;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements ISlideService {

    private final SlideRepository repository;

    private final OrganizationRepository organizationRepository;

    private final SlideMapper mapper;

    private final MessageSource messageSource;

    @Override
    public List<SlideBasicResponseDto> getAll() {
        List<Slide> slides =  repository.findAllByOrderByPositionAsc();
        if (slides.isEmpty())
            throw new EmptyListException(messageSource.getMessage("empty-list", null, Locale.US));
        return mapper.slideEntityList2DtoList(slides);
    }

    public SlideResponseDto create(SlideRequestDto dto) {

        Organization org = organizationRepository.findAll().get(0);

        Slide slide = mapper.slideDTO2SlideEntity(dto, org);

        List<Slide> slidesList = repository.findAll();

        int n = 0;

        if (dto.getPosition() == null){
            slide.setPosition(slidesList.size() + 1);
            slidesList.add(slide);
            n++;
        } else if (slidesList.isEmpty() || (slidesList.size() < dto.getPosition())) {
            slide.setPosition(slidesList.size()+1);
            slidesList.add(slide);
        } else if (!slidesList.isEmpty() && (slidesList.size() >= dto.getPosition())) {
            slide.setPosition(dto.getPosition());
            slidesList.add(dto.getPosition(), slide);
        }

        SlideResponseDto responseDTO = mapper.slideEntity2SlideDTO(repository.save(slide));

        if (n == 1)
            responseDTO.setMessage(messageSource.getMessage("slide-position", null, Locale.US));

        return responseDTO;
    }

    public SlideResponseDto update(SlideRequestDto dto, Long id) {
        Slide entity = getSlideById(id);
        try {
            entity.setImageUrl(dto.getImageUrl());
            entity.setText(dto.getText());
            entity.setPosition(dto.getPosition());
            repository.save(entity);
            return mapper.slideEntity2SlideDTO(entity);
        } catch (Exception e) {
            throw new UnableToUpdateEntityException(messageSource.getMessage("unable-to-update-entity", new Object[] { "Slide" }, Locale.US));
        }
    }

    private Slide getSlideById(Long id) {
        Optional<Slide> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found",new Object[] { "Entity with Id: " + id } ,Locale.US));
        return entity.get();
    }
}
