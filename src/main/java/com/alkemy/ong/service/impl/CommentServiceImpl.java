package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.comment.CommentBodyResponseDto;
import com.alkemy.ong.dto.comment.CommentRequestDto;
import com.alkemy.ong.dto.comment.CommentResponseDto;
<<<<<<< HEAD
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.mapper.CommentMapper;
=======
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.UnableToUpdateEntityException;
import com.alkemy.ong.mapper.GenericMapper;
>>>>>>> develop
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.service.ICommentService;

import lombok.RequiredArgsConstructor;

import com.alkemy.ong.repository.CommentRepository;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
>>>>>>> develop
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

<<<<<<< HEAD
    private CommentRepository commentRepository;
    private MessageSource messageSource;
    private CommentMapper commentMapper;
=======

    private final CommentRepository repository;
    private final GenericMapper mapper;
    private final MessageSource messageSource;

>>>>>>> develop


    @Override
    public CommentResponseDto save(CommentRequestDto commentRequestDto) {
        Comment comment = mapper.map(commentRequestDto, Comment.class);
        Comment savedComment = repository.save(comment);

        return mapper.map(savedComment, CommentResponseDto.class);

    }

    //TODO to review as required
    @Override
    public void delete(Long id) {
    repository.deleteById(id);
    }


    //TODO to review as required
    // @Override
    public CommentResponseDto update(Long id, CommentRequestDto dto) {
        Comment entity = getCommentById(id);
        try {
            Comment updatedEntity = mapper.map(dto, Comment.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setCreationDate(entity.getCreationDate());
            updatedEntity.setUpdateDate(LocalDateTime.now());
            repository.save(updatedEntity);
            return mapper.map(updatedEntity, CommentResponseDto.class);
        } catch (Exception e) {
            throw new UnableToUpdateEntityException(messageSource.getMessage("unable-to-update-entity", new Object[]{id}, Locale.US));
        }

    }
    //TODO to review as required
    private Comment getCommentById(Long id) {
        Optional<Comment> comment = repository.findById(id);
        if(comment.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("not-found", new Object[]{id}, Locale.US));
        }
        return comment.get();
    }

    @Override
    public List<CommentBodyResponseDto> getAllBodies() {
        if(commentRepository.findAllByOrderByNewsCreationDateAsc().isEmpty()){
            throw new EmptyListException(messageSource.getMessage("empty-list", null, Locale.US));
        }
        return mapper.mapAll(commentRepository.findAllByOrderByNewsCreationDateAsc(), CommentBodyResponseDto.class);
    }

}
