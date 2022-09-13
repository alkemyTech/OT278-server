package com.alkemy.ong.exception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomExceptionDetails {

    private String exception;
    private String message;
    private String path;

    public CustomExceptionDetails(Exception e, String path){
        this.exception = e.getClass().getSimpleName();
        this.message = e.getMessage();
        this.path = path;
    }

    @Override
    public String toString() {
        return "{" +
            " exception='" + getException() + "'" +
            ", message='" + getMessage() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
