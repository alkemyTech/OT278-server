package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactDto;

import java.util.Set;

public interface IContactService {

    ContactDto save(ContactDto dto);
    Set<ContactDto> findAll();
}
