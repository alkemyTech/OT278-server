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
     public OrganizationPublicDTO orgDto () {
        OrganizationPublicDTO getOrgDto  = new OrganizationPublicDTO();
        return getOrgDto;

    };


}