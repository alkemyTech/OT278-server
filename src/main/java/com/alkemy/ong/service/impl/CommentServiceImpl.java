package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.comment.CommentBodyResponseDto;
import com.alkemy.ong.dto.comment.CommentRequestDto;
import com.alkemy.ong.dto.comment.CommentResponseDto;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.service.ICommentService;

import lombok.RequiredArgsConstructor;

import com.alkemy.ong.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private CommentRepository commentRepository;
    private MessageSource messageSource;
    private CommentMapper commentMapper;


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


    //TODO to review as required
    // @Override
    public CommentResponseDto put(Long id, CommentRequestDto edit) {

        try {
            Comment savedComment = this.getCommentById(id);
            savedComment.setBody(edit.getBody());
            Comment editComment = commentRepository.save(savedComment);
            CommentResponseDto saveDto = commentMapper.entity2CommentDto(editComment);
            return saveDto;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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

    @Override
    public List<CommentBodyResponseDto> getAllBodies() {
        if(commentRepository.findAllByOrderByNewsCreationDateAsc().isEmpty()){
            throw new EmptyListException(messageSource.getMessage("empty-list", null, Locale.US));
        }
        return mapper.mapAll(commentRepository.findAllByOrderByNewsCreationDateAsc(), CommentBodyResponseDto.class);
    }

}
