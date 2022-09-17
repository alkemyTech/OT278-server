package com.alkemy.ong.mapper;

import com.alkemy.ong.controller.TestimonialController;
import com.alkemy.ong.dto.testimonial.TestimonialModel;
import com.alkemy.ong.model.Testimonial;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TestimonialModelAssembler extends RepresentationModelAssemblerSupport<Testimonial, TestimonialModel> {
    public TestimonialModelAssembler() {
        super(TestimonialController.class, TestimonialModel.class);
    }

    @Override
    public TestimonialModel toModel(Testimonial entity) {
        TestimonialModel model = new TestimonialModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}