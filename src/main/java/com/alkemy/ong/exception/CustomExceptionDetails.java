package com.alkemy.ong.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ApiModel(value = "Model Error Response")
public class CustomExceptionDetails {

    @ApiModelProperty(value = "Error/Exception name", example = "ErrorException", position = 0)
    private String exception;
    @ApiModelProperty(value = "Details of message", example = "An Error ocurred when...", position = 1)
    private String message;
    @ApiModelProperty(value = "Path where the error become", example = "resouce/service/method", position = 2)
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
