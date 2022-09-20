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
                .photo("token-url")
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

        private final String ENDPOINT_URL = "/users";

        @Test
        void whenListIsNotEmpty_shouldReturnAll_status200() throws Exception {
            users.add(userDto);

            when(service.getAll()).thenReturn(users);

            mockMvc.perform(get(ENDPOINT_URL)
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.[0:].firstName").value(userDto.getFirstName()));

            verify(service).getAll();
        }

        @Test
        void whenListIsEmpty_shouldThrowEmptyListException_status200() throws Exception {
            when(service.getAll()).thenThrow(EmptyListException.class);

            mockMvc.perform(get(ENDPOINT_URL)
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.exception").value(EmptyListException.class.getSimpleName()))
                    .andExpect(jsonPath("$.path").value(ENDPOINT_URL));

            verify(service).getAll();
        }
    }

    @Nested
    public class GetLoggerUserDataTest {

        private final String ENDPOINT_URL = "/users/me";
        private final String PARAM_NAME = "authorization";

        @ParameterizedTest
        @ValueSource(strings = { "validToken" })
        void whenValidTokenEntered_shouldReturnDto_status200(String jwt) throws Exception {
            assertEquals("validToken", jwt);

            UserResponseDto dto = UserResponseDto.builder()
                    .id(1L)
                    .firstName("firstName")
                    .lastName("lastName")
                    .email("userResponseDto@email.com")
                    .photo("token-url")
                    .role(role)
                    .token(jwt)
                    .build();

            when(service.getLoggerUserData(jwt)).thenReturn(dto);

            mockMvc.perform(get(ENDPOINT_URL).header(PARAM_NAME, jwt)
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
        }

        @ParameterizedTest
        @ValueSource(strings = { "invalidToken" })
        void whenInvalidTokenEntered_shouldThrowForbiddenException_status403(String jwt) throws Exception {
            assertEquals("invalidToken", jwt);

            when(service.getLoggerUserData(jwt)).thenThrow(ForbiddenException.class);

            mockMvc.perform(get(ENDPOINT_URL).header(PARAM_NAME, jwt)
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isForbidden())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.exception").value(ForbiddenException.class.getSimpleName()))
                    .andExpect(jsonPath("$.path").value(ENDPOINT_URL));

            verify(service).getLoggerUserData(jwt);
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = { " ", "\t", "\n" })
        void whenEmptyTokenEntered_shouldThrowBadRequestException_status400(String jwt) throws Exception {
            assertTrue(jwt.trim().isEmpty());

            when(service.getLoggerUserData(jwt)).thenThrow(BadRequestException.class);

            mockMvc.perform(get(ENDPOINT_URL).header(PARAM_NAME, jwt)
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.exception").value(BadRequestException.class.getSimpleName()))
                    .andExpect(jsonPath("$.path").value(ENDPOINT_URL));

            verify(service).getLoggerUserData(jwt);
        }
    }

    @Nested
    public class DeleteTest {

        private final String ENDPOINT_URL = "/users/";
        private final long INVALID_ID = -2L;
        private final long TEST_ID = 00000000000001L;
        private final long VALID_ID = 123456L;

        @ParameterizedTest
        @ValueSource(longs = { VALID_ID, TEST_ID })
        void whenValidIdEntered_shouldReturnStatus200(Long id) throws Exception {
            assertTrue(id > 0);

            when(service.delete(id)).thenReturn(true);

            mockMvc.perform(delete(ENDPOINT_URL + id))
                    .andExpect(status().isOk());

            verify(service).delete(id);
        }

        @ParameterizedTest
        @ValueSource(longs = { VALID_ID })
        void whenValidIdEntered_butNotFound_shouldThrowNotFoundException_Status404(Long id) throws Exception {
            assertTrue(id > 0);

            when(service.delete(VALID_ID)).thenThrow(NotFoundException.class);

            mockMvc.perform(delete(ENDPOINT_URL + VALID_ID))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.exception").value(NotFoundException.class.getSimpleName()))
                    .andExpect(jsonPath("$.path").value(ENDPOINT_URL + VALID_ID));

            verify(service).delete(VALID_ID);
        }

        @ParameterizedTest
        @ValueSource(longs = { INVALID_ID })
        void whenInvalidIdEntered_shouldThrowBadRequestException_Status400(long id) throws Exception {
            assertFalse(id > 0);

            when(service.delete(INVALID_ID)).thenThrow(BadRequestException.class);

            mockMvc.perform(delete(ENDPOINT_URL + INVALID_ID))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.exception").value(BadRequestException.class.getSimpleName()))
                    .andExpect(jsonPath("$.path").value(ENDPOINT_URL + INVALID_ID));

            verify(service).delete(INVALID_ID);
        }
    }
}