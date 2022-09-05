package com.alkemy.ong.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialRequestDto implements Serializable {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String image;

    @NotBlank(message = "Content cannot be empty")
    private String content;
}
