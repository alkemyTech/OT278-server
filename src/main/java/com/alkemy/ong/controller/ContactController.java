package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.IContactService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final IContactService service;

    @PostMapping
    public ResponseEntity<ContactDto> save(@Valid @RequestBody ContactDto contact) {
        ContactDto savedContact = service.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @GetMapping
    public ResponseEntity<Set<ContactDto>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }
}
