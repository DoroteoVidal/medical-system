package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientUserDto {

    @NotBlank
    private String address;

    private Long phone;

    @NotBlank
    private String genre;

    @NotNull
    private Long dni;

    public PatientUserDto() {}

    public PatientUserDto(String address, Long phone, String genre, Long dni) {
        this.address = address;
        this.phone = phone;
        this.genre = genre;
        this.dni = dni;
    }
}
