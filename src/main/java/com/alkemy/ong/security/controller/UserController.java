package com.alkemy.ong.security.controller;

import com.alkemy.ong.security.auth.UserService;
import com.alkemy.ong.security.dto.AuthenticationRequest;
import com.alkemy.ong.security.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/:{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
