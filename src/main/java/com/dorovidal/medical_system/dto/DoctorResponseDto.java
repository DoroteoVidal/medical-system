package com.dorovidal.medical_system.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class DoctorResponseDto {
    private Long id;
    private String address;
    private String specialty;
    private String biography;
    private Long phone;
    private String gender;
    private Long dni;
    private String email;
    private LocalDate dateOfBirth;
    private String name;
    private String lastname;
}
