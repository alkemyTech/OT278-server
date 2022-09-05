package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.ContactRequestDto;
import com.alkemy.ong.dto.response.ContactResponseDto;

public interface IContactService {

    ContactResponseDto save(ContactRequestDto dto);
}
