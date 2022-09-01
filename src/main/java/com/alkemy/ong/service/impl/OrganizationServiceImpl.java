package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    private OrganizationRepository repository;
    @Autowired
    private OrganizationMapper mapper;

    public OrganizationDto update(OrganizationDto edited){
        Organization organization = mapper.organizationDto2Entity(edited);
        organization.setId(1L);
        return mapper.organizationEntity2Dto(repository.save(organization));
    }
}
