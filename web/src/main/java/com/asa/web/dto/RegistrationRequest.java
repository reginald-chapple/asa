package com.asa.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    private String password;

    // @NotBlank
    // private String confirmPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
