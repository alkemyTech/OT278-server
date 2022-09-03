package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.UnableToSaveEntityException;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactMapper mapper;
    private final ContactRepository repository;
    private final MessageSource messageSource;

    public ContactDto save(ContactDto dto) {
        try {
            Contact entity = mapper.contactDto2ContactEntity(dto);
            Contact savedEntity = repository.save(entity);
            return mapper.contactEntity2ContactDto(savedEntity);
        } catch (Exception e) {
            throw new UnableToSaveEntityException(messageSource.getMessage("unable-to-save-entity", null, Locale.US));
        }
    }

    public Set<ContactDto> findAll(){
        List<Contact> contacts = repository.findAll();
        Set<ContactDto> contactDtos = new HashSet<>();
        ContactDto dto;
        for(Contact contact : contacts){
            dto = new ContactDto();
            dto = mapper.contactEntity2ContactDto(contact);
            contactDtos.add(dto);
        }
        return contactDtos;
    }
}
