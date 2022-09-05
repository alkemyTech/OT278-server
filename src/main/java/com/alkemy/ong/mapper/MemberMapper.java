package com.alkemy.ong.mapper;

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.request.MemberRequestDto;
import com.alkemy.ong.dto.response.MemberResponseDto;
import com.alkemy.ong.model.Member;

@Component
public class MemberMapper {
    
    public Member memberDto2MemberEntity(MemberRequestDto dto) {
        Member entity = new Member();
        
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setFacebookUrl(dto.getFacebookUrl());
        entity.setInstagramUrl(dto.getInstagramUrl());
        entity.setLinkedinUrl(dto.getLinkedinUrl());
        entity.setImage(dto.getImage());

        return entity;
    }

    public MemberResponseDto memberEntity2MemberDto(Member entity) {        
       MemberResponseDto dto = new MemberResponseDto();

       dto.setId(entity.getId());
       dto.setName(entity.getName());
       dto.setDescription(entity.getDescription());
       dto.setCreationDate(entity.getCreationDate());
       dto.setFacebookUrl(entity.getFacebookUrl());
       dto.setInstagramUrl(entity.getInstagramUrl());
       dto.setLinkedinUrl(entity.getLinkedinUrl());
       dto.setDeleted(entity.getDeleted());
       dto.setImage(entity.getImage());

       return dto;
    }
}
