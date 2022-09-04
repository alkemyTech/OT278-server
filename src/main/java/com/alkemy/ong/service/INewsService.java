package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import javassist.NotFoundException;

public interface INewsService {

    News getNewsById(Long id) throws NotFoundException;
    NewsDto getById(Long id) throws NotFoundException;
}
