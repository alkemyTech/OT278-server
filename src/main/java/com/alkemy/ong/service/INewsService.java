package com.alkemy.ong.service;

import com.alkemy.ong.dto.response.NewsResponseDto;

public interface INewsService {

    NewsResponseDto getById(Long id) ;
}
