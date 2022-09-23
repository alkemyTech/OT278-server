package com.alkemy.ong.controller;

import com.alkemy.ong.dto.activity.ActivityRequestDTO;
import com.alkemy.ong.dto.activity.ActivityResponseDTO;
import com.alkemy.ong.exception.AlreadyExistsException;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.mapper.GenericMapper;
import com.alkemy.ong.service.IActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
public class ActivityControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private IActivityService service;
    private ActivityRequestDTO activityDto;
    private  ActivityRequestDTO modified;
    private  ActivityRequestDTO existingName;
    @Autowired
    private GenericMapper genericMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        activityDto = new ActivityRequestDTO("test activity","testcontent","urlactivitytest");
        modified = new ActivityRequestDTO(
                "test activity modified",
                "test content modified",
                "urlactivitytest/modified");
        existingName = new ActivityRequestDTO("name1","content1","image1");

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewActivity_shouldReturnCreated() throws Exception{
        ActivityResponseDTO responseDTO = genericMapper.map(activityDto, ActivityResponseDTO.class);
        responseDTO.setId(1L);
        when(service.create(activityDto)).thenReturn(responseDTO);

        mockMvc.perform(post("/activities")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(activityDto))
                .with(user("admin").roles("ADMIN"))
                .with(csrf()))
                .andExpect(status().isCreated());
        verify(service).create(activityDto);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewActivity_null_shouldReturnBadRequest() throws Exception{
        ActivityRequestDTO nullActivity = new ActivityRequestDTO();
        when(service.create(nullActivity)).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/activities")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDto))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateActivity_shouldReturnOk() throws Exception{
        Long id = 1L;
        when(service.update(id,modified)).thenReturn(genericMapper.map(modified, ActivityResponseDTO.class));

        mockMvc.perform(put("/activities/" + id)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modified))
                .with(user("admin").roles("ADMIN"))
                .with(csrf()))
        .andExpect(status().isOk());

        verify(service).update(id,modified);
    }
}
