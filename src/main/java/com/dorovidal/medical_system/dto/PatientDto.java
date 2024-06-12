package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    private String address;

    private Long phone;

    @NotBlank
    private String genre;

    @NotNull
    private Long dni;

    public PatientDto() {}

    public PatientDto(String name, String lastname, LocalDate dateOfBirth, String address, Long phone, String genre, Long dni) {
        this.name = name;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.genre = genre;
        this.dni = dni;
    }
}
