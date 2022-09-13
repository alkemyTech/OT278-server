package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.comment.CommentRequestDto;
import com.alkemy.ong.dto.comment.CommentResponseDto;
import com.alkemy.ong.exception.ForbiddenException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.UnableToUpdateEntityException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.security.auth.UserService;
import com.alkemy.ong.security.dto.UserResponseDto;
import com.alkemy.ong.security.jwt.JwtUtils;
import com.alkemy.ong.security.model.User;
import com.alkemy.ong.service.ICommentService;
import com.alkemy.ong.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public CommentResponseDto save(CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.commentDto2Entity(commentRequestDto);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.entity2CommentDto(savedComment);

    }

    //TODO to review as required
    @Override
    public void delete(Long id) {
    commentRepository.deleteById(id);
    }

    @Override
    public CommentResponseDto update(Long id, CommentRequestDto edit,String auth) {
        UserResponseDto userResponseDto = userService.getLoggerUserData(auth);
        if (userResponseDto.getId() != commentRepository.getById(id).getUserId()){
            if (userResponseDto.getRole().getName().name() != "ADMIN"){
                throw new ForbiddenException(messageSource.getMessage("forbidden",null,Locale.US));
            }
        }
        Optional<Comment> exists = commentRepository.findById(id);
        if (!exists.isPresent()){
            throw new NotFoundException(messageSource.getMessage("not-found",new Object[]{id}, Locale.US));
        }
        try{
            Comment comment = commentMapper.commentDto2Entity(edit);
            comment.setId(id);
            return commentMapper.entity2CommentDto(commentRepository.save(comment));
        }catch (Exception e){
            throw new UnableToUpdateEntityException(messageSource.getMessage("unable-to-update-entity",
                    new Object[]{id},Locale.US));
        }
    }

    //TODO to review as required
    public Comment getCommentById(Long id) throws Exception {
        Optional<Comment> savedComment = commentRepository.findById(id);
        if(!savedComment.isPresent()){
            throw new Exception("invalid ID");
        }
        return savedComment.get();
    }

}
