package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class OrganizationDto {
    private String name;
    private String img;
    private String address;
    @Pattern(regexp = "^(\\+?\\d{1,3})?(\\d{10})$", message = "the number phone es invalid")
    private String phone;
    @Email(regexp = "^[a-zA-Z]+((\\.|_)*[a-zA-Z0-9]+)*((\\.|_)[a-zA-Z0-9]+)*@[a-z]+\\.\\w\\w\\w(\\.\\w\\w)?$", message = "the email is invalid")
    private String email;
    private String welcomeText;
    private String aboutUs;

    @Pattern(regexp = "^\\s*(http\\:\\/\\/)?facebook\\.com\\/[a-z\\d-_]{1,255}\\s*$/i", 
            message = "The url entered does not match the pattern")
    private String facebookUrl;
    @Pattern(regexp = "^\\s*(http\\:\\/\\/)?instagram\\.com\\/[a-z\\d-_]{1,255}\\s*$/i", 
            message = "The url entered does not match the pattern")
    private String instagramUrl;
    @Pattern(regexp = "^\\s*(http\\:\\/\\/)?linkedin\\.com\\/[a-z\\d-_]{1,255}\\s*$/i", 
            message = "The url entered does not match the pattern")
    private String linkedinUrl;
}
