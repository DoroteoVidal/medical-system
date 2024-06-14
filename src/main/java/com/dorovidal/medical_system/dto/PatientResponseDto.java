package com.dorovidal.medical_system.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String name;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private Long phone;
    private String gender;
    private Long dni;
}
