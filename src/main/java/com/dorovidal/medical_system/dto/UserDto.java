package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    public UserDto(String email, String password, LocalDate dateOfBirth, String name, String lastname) {
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.lastname = lastname;
    }
}
