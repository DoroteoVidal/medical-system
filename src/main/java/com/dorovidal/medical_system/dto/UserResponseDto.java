package com.dorovidal.medical_system.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String name;
    private String lastname;
    private boolean enabled;
    private String role;
}
