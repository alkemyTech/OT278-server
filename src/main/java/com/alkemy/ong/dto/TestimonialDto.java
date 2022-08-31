package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialDto {

    private long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    private String image;

    @NotNull(message = "Content cannot be empty")
    private String content;
}
