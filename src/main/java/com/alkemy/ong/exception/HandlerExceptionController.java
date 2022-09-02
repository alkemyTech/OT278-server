package com.alkemy.ong.exception;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class HandlerExceptionController {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public CustomExceptionDetails notFoundRequest(HttpServletRequest request, Exception exception) {
        return new CustomExceptionDetails(exception, request.getRequestURI());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler({
            AlreadyExistsException.class,
            UnableToSaveEntityException.class
    })
    @ResponseBody
    public CustomExceptionDetails elementAlreadyExists(HttpServletRequest request, Exception exception) {
        return new CustomExceptionDetails(exception, request.getRequestURI());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CustomExceptionDetails fatalErrorUnexpectedException(HttpServletRequest request, Exception exception){
        return new CustomExceptionDetails(exception,request.getRequestURI());
    }
}
