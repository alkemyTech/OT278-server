package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactDto;

import java.util.List;

public interface IContactService {

    ContactDto save(ContactDto dto);
    List<ContactDto> findAll();
}
