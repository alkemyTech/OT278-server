package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.CommentRequestDto;
import com.alkemy.ong.dto.response.CommentResponseDto;

public interface ICommentService {

    //TODO to review as required
    CommentResponseDto save(CommentRequestDto commentRequestDto);

    //TODO to review as required
    void delete(Long id);

    //TODO to review as required
    CommentResponseDto put(Long id, CommentRequestDto edit);


}
