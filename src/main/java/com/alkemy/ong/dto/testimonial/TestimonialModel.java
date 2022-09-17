package com.alkemy.ong.dto.testimonial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialModel extends RepresentationModel<TestimonialModel> {

    private Long id;
    private String name;
    private String image;
    private String content;
}
