package com.alkemy.ong.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.member.MemberRequestDto;
import com.alkemy.ong.dto.member.MemberResponseDto;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements IMemberService{

    private final MemberRepository memberRepository;
    private final MemberMapper mapper;

    @Override
    public MemberResponseDto create(MemberRequestDto dto) {
        Member member = mapper.memberDto2MemberEntity(dto);

        member.setCreationDate(LocalDateTime.now());
        member.setDeleted(false);

        /*
            TODO: <- ImageService should validate and return the path of the File...
            example:
            member.setImage(imageService.getImage(dto.getImage()));
        */

        Member memberSaved = memberRepository.save(member);

        return mapper.memberEntity2MemberDto(memberSaved);
    }
    
}
