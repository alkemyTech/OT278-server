package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.ContactRequestDto;

public interface IContactService {

    ContactRequestDto save(ContactRequestDto dto);
}
