package com.alkemy.ong.security.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import com.alkemy.ong.enums.RoleEnum;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.exception.ForbiddenException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.security.SecurityConfiguration;
import com.alkemy.ong.security.auth.UserService;
import com.alkemy.ong.security.dto.UserDto;
import com.alkemy.ong.security.dto.UserResponseDto;
import com.alkemy.ong.security.jwt.JwtRequestFilter;
import com.alkemy.ong.security.model.Role;

@WebMvcTest(value = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfiguration.class,
                JwtRequestFilter.class }) }, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private static List<UserDto> users;
    private static UserDto userDto;
    private static Role role;

    @BeforeAll
    static void init() {
        role = new Role(1L,
                RoleEnum.ADMIN,
                "description",
                LocalDateTime.now(),
                LocalDateTime.now());

        userDto = UserDto.builder()
                .id(1L)
                .firstName("expectedValue")
                .lastName("lastName")
                .email("userDto@email.com")
                .photo("photo.jpg")
                .role(role)
                .build();

        users = new ArrayList<>();
    }

    @Order(1)
    @ParameterizedTest
    @EnumSource(value = RoleEnum.class, names = { "ADMIN" })
    void testUserController_WhenUserRoleIsNotAdmin_ShouldThrowForbiddenException(RoleEnum roleUserLogged) {

        assertTrue(roleUserLogged.name().equalsIgnoreCase(RoleEnum.ADMIN.name()));
    }

    @Nested
    public class GetAllTest {

        @Test    
        void whenListIsNotEmpty_shouldReturnAll_status200() {
            try {
                users.add(userDto);

                when(service.getAll()).thenReturn(users);

                mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(APPLICATION_JSON))
                        .andExpect(jsonPath("$.[0:].firstName").value("expectedValue"));

                verify(service).getAll();

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }

        @Test       
        void whenListIsEmpty_shouldThrowEmptyListException_status200() {
            try {
                when(service.getAll()).thenThrow(EmptyListException.class);

                mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(APPLICATION_JSON))
                        .andExpect(jsonPath("$.exception").value("EmptyListException"))
                        .andExpect(jsonPath("$.path").value("/users"));

                verify(service).getAll();

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }
    }

    @Nested
    public class GetLoggerUserDataTest {

        @ParameterizedTest
        @ValueSource(strings = { "validToken" })        
        void whenValidTokenEntered_shouldReturnDto_status200(String jwt) {
            assertEquals("validToken", jwt);

            try {
                UserResponseDto dto = UserResponseDto.builder()
                        .id(1L)
                        .firstName("firstName")
                        .lastName("lastName")
                        .email("userResponseDto@email.com")
                        .photo("photo.jpg")
                        .role(role)
                        .token(jwt)
                        .build();

                when(service.getLoggerUserData(jwt)).thenReturn(dto);

                mockMvc.perform(get("/users/me").header("authorization", jwt)
                        .contentType(APPLICATION_JSON)
                        .content(jwt))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(dto.getId()))
                        .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                        .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                        .andExpect(jsonPath("$.token").value(jwt))
                        .andExpect(jsonPath("$.email").value(dto.getEmail()))
                        .andExpect(jsonPath("$.photo").value(dto.getPhoto()))
                        .andExpect(jsonPath("$.role.name").value(dto.getRole().getName().name()));

                verify(service).getLoggerUserData(jwt);

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }

        @ParameterizedTest
        @ValueSource(strings = { "invalidToken" })
        void whenInvalidTokenEntered_shouldThrowForbiddenException_status403(String jwt) {

            assertEquals("invalidToken", jwt);

            try {
                when(service.getLoggerUserData(jwt)).thenThrow(ForbiddenException.class);

                mockMvc.perform(get("/users/me").header("authorization", jwt)
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isForbidden())
                        .andExpect(content().contentType(APPLICATION_JSON))
                        .andExpect(jsonPath("$.exception").value("ForbiddenException"))
                        .andExpect(jsonPath("$.path").value("/users/me"));

                verify(service).getLoggerUserData(jwt);

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void whenEmptyTokenEntered_shouldThrowBadRequestException_status400(String jwt) {

            assertTrue(jwt.trim().isEmpty());

            try {
                when(service.getLoggerUserData(jwt)).thenThrow(BadRequestException.class);

                    mockMvc.perform(get("/users/me").header("authorization", jwt)
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().contentType(APPLICATION_JSON))
                        .andExpect(jsonPath("$.exception").value("BadRequestException"))
                        .andExpect(jsonPath("$.path").value("/users/me"));

                verify(service).getLoggerUserData(jwt);

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }
    }

    @Nested
    public class DeleteTest {

        private final long INVALID_ID = -2L;
        private final long TEST_ID = 00000000000001L;
        private final long VALID_ID = 123456L;

        @ParameterizedTest
        @ValueSource(longs = { VALID_ID, TEST_ID })
        void whenValidIdEntered_shouldReturnStatus200(Long id) {

            assertTrue(id > 0);

            try {
                when(service.delete(id)).thenReturn(true);

                mockMvc.perform(delete("/users/" + id))
                        .andExpect(status().isOk());

                verify(service).delete(id);

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }

        @ParameterizedTest
        @ValueSource(longs = { VALID_ID })
        void whenValidIdEntered_butNotFound_shouldThrowNotFoundException_Status404(Long id) {

            assertTrue(id > 0);

            try {
                when(service.delete(VALID_ID)).thenThrow(NotFoundException.class);

                mockMvc.perform(delete("/users/" + VALID_ID))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(APPLICATION_JSON))
                        .andExpect(jsonPath("$.exception").value("NotFoundException"))
                        .andExpect(jsonPath("$.path").value("/users/" + VALID_ID));

                verify(service).delete(VALID_ID);

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }

        @ParameterizedTest
        @ValueSource(longs = { INVALID_ID })
        void whenInvalidIdEntered_shouldThrowBadRequestException_Status400(long id) {

            assertFalse(id > 0);

            try {
                when(service.delete(INVALID_ID)).thenThrow(BadRequestException.class);

                mockMvc.perform(delete("/users/" + INVALID_ID))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().contentType(APPLICATION_JSON))
                        .andExpect(jsonPath("$.exception").value("BadRequestException"))
                        .andExpect(jsonPath("$.path").value("/users/" + INVALID_ID));

                verify(service).delete(INVALID_ID);

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: must be refactored..
                fail("Logger messageSource");
            }
        }
    }

}