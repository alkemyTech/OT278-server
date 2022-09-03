package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.IContactService;
import com.alkemy.ong.service.IEmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final IContactService service;
    private final IEmailService emailService;

    @PostMapping
    public ResponseEntity<ContactDto> save(@Valid @RequestBody ContactDto contact) {
        ContactDto savedContact = service.save(contact);
        emailService.sendEmail(savedContact.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

}
