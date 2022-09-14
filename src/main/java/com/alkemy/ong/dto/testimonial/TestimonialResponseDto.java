package com.alkemy.ong.dto.testimonial;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialResponseDto {

    @ApiModelProperty(position = 0)
    private Long id;
    private String name;
    private String image;
    private String content;
}
