package com.alkemy.ong.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.exception.*;
import com.alkemy.ong.mapper.GenericMapper;
import com.alkemy.ong.service.IMemberService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.member.MemberRequestDto;
import com.alkemy.ong.dto.member.MemberResponseDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements IMemberService {

    private final MemberRepository repository;
    private final MessageSource messageSource;
    private final GenericMapper mapper;

    @Override
    public MemberResponseDto create(MemberRequestDto dto) {
        Member memberSaved;
        try {
            Member member = mapper.map(dto, Member.class);

            member.setCreationDate(LocalDateTime.now());
            member.setDeleted(false);

            /*
             * TODO: <- ImageService should validate and return the path of the File...
             * example:
             * member.setImage(imageService.getImage(dto.getImage()));
             */

            memberSaved = repository.save(member);
        } catch (Exception e) {
            throw new UnableToSaveEntityException(
                    messageSource.getMessage("unable-to-save-member", null, Locale.US));
        }

        return mapper.map(memberSaved, MemberResponseDto.class);
    }

    @Override
    public List<MemberResponseDto> findAll() {
        List<Member> members = repository.findAll();
        if (members.isEmpty()) {
            throw new EmptyListException(messageSource.getMessage("empty-list", null, Locale.US));
        }
        return mapper.mapAll(members, MemberResponseDto.class);
    }

    @Override
    public PageDto<MemberResponseDto> getPage(int pageNum) {
        int size = 10;
        if (pageNum < 0)
            throw new BadRequestException(messageSource.getMessage("negative-page-number", null, Locale.US));
        Pageable pageable = PageRequest.of(pageNum, size);
        Page<Member> page = repository.findAll(pageable);
        if (page.getTotalPages() == 0)
            throw new EmptyListException(messageSource.getMessage("empty-list", null, Locale.US));
        if (page.isEmpty())
            throw new NotFoundException(messageSource.getMessage("last-page-is", new Object[]{page.getTotalPages() - 1}, Locale.US));
        return mapper.mapPage(page, MemberResponseDto.class, "member");
    }


    public MemberResponseDto update(MemberRequestDto dto, Long id) {
        Member entity = getMemberById(id);
        try {
            entity.setName(dto.getName());
            entity.setFacebookUrl(dto.getFacebookUrl());
            entity.setInstagramUrl(dto.getInstagramUrl());
            entity.setLinkedinUrl(dto.getLinkedinUrl());
            entity.setImage(dto.getImage());
            entity.setDescription(dto.getDescription());
            entity.setUpdateDate(LocalDateTime.now());
            repository.save(entity);
            return mapper.map(entity, MemberResponseDto.class);
        } catch (Exception e) {
            throw new UnableToUpdateEntityException(messageSource.getMessage("unable-to-update-member", new Object[] {id}, Locale.US));
        }
    }

    public void delete(Long id) {
        Member entity = getMemberById(id);
        try {
            entity.setUpdateDate(LocalDateTime.now());
            repository.deleteById(id);
        } catch (Exception e) {

            throw new UnableToDeleteEntityException(messageSource.getMessage("unable-to-delete-member", new Object[] {id}, Locale.US));

        }
    }

    private Member getMemberById(Long id) {
        Optional<Member> entity = repository.findById(id);
        if (entity.isEmpty())

            throw new NotFoundException(messageSource.getMessage("member-not-found", new Object[] {id} ,Locale.US));

        return entity.get();
    }

}
