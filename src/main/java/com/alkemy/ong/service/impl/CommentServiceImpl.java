package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.comment.CommentBodyResponseDto;
import com.alkemy.ong.dto.comment.CommentRequestDto;
import com.alkemy.ong.dto.comment.CommentResponseDto;

import com.alkemy.ong.exception.EmptyListException;

import com.alkemy.ong.exception.ForbiddenException;

import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.UnableToUpdateEntityException;
import com.alkemy.ong.mapper.GenericMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.security.auth.UserService;
import com.alkemy.ong.security.dto.UserResponseDto;
import com.alkemy.ong.security.jwt.JwtUtils;
import com.alkemy.ong.service.ICommentService;

import java.util.List;

import com.alkemy.ong.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository repository;
    private final GenericMapper mapper;
    private final MessageSource messageSource;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

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

    @Override
    public CommentResponseDto update(Long id, CommentRequestDto edit,String auth) {
        UserResponseDto userResponseDto = userService.getLoggerUserData(auth);
        if (userResponseDto.getId() != repository.getById(id).getUserId()){
            if (userResponseDto.getRole().getName().name() != "ADMIN"){
                throw new ForbiddenException(messageSource.getMessage("forbidden",null,Locale.US));
            }
        }
        Optional<Comment> exists = repository.findById(id);
        if (exists.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("comment-not-found",null, Locale.US));
        }
        try{
            Comment comment = mapper.map(edit, Comment.class);
            comment.setId(id);
            return mapper.map(repository.save(comment), CommentResponseDto.class);
        }catch (Exception e){
            throw new UnableToUpdateEntityException(messageSource.getMessage("unable-to-update-comment",
                    null, Locale.US));
        }
    }

    //TODO to review as required
    private Comment getCommentById(Long id) {
        Optional<Comment> comment = repository.findById(id);
        if(comment.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("comment-not-found", null, Locale.US));
        }
        return comment.get();
    }

    @Override
    public List<CommentBodyResponseDto> getAllCommentBodies() {
        List<Comment> bodies = repository.findAllByOrderByNewsCreationDateAsc();
        if(bodies.isEmpty()){
            throw new EmptyListException(messageSource.getMessage("empty-list", null, Locale.US));
        }
        return mapper.mapAll(bodies, CommentBodyResponseDto.class);
    }

}
