package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationPublicDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("organization")
public class OrganizationController {

    @GetMapping("/public")
    public ResponseEntity<List<OrganizationPublicDTO>> getOrgPublicData() {
        List<OrganizationPublicDTO> OrganizationPublicDTO = null;
        return ResponseEntity.ok().body(OrganizationPublicDTO);
        //falta Model de Organizacion para continuar con el service/mapper para hacer efectivo
    }

}