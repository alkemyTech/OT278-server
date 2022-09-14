package com.alkemy.ong.dto.testimonial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialRequestDto implements Serializable {

    @ApiModelProperty(dataType = "String", allowEmptyValue = false, required = true, example = "Lucas testimony", position = 0)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @ApiModelProperty(dataType = "String", required = false, example = "image-token.jpeg")
    private String image;

    @ApiModelProperty(dataType = "String", required = true, example = "Here is the testimonial of Lucas", position = 1)
    @NotBlank(message = "Content cannot be empty")
    private String content;
}
