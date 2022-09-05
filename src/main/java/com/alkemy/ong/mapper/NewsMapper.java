package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.request.NewsRequestDto;
import com.alkemy.ong.dto.response.NewsResponseDto;
import com.alkemy.ong.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

    public News newsDto2NewsEntity(NewsRequestDto dto) {
        News entity = new News();
        entity.setName(dto.getName());
        entity.setContent(dto.getContent());
        entity.setImage(dto.getImage());
        return entity;
    }

    public NewsResponseDto newsEntity2NewsDto(News entity) {
        NewsResponseDto dto = new NewsResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setContent(entity.getContent());
        dto.setImage(entity.getImage());
        return dto;
    }
}
