package com.alkemy.ong.security.dto;

import lombok.Getter;
import lombok.Setter;
import com.alkemy.ong.security.model.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRequestDto {

    @NotNull(message = "first name cannot be null")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    private String lastName;

    @NotNull(message = "email cannot be null")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotNull(message = "password cannot be null")
    private String password;

    private String photo;

    private Role role;

}
