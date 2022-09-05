package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.MemberRequestDto;
import com.alkemy.ong.dto.response.MemberResponseDto;
import java.util.List;

public interface IMemberService {
    
    MemberResponseDto create(MemberRequestDto dto);

    List<MemberResponseDto> findAll();
}
