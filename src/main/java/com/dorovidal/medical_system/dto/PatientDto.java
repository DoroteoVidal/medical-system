package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Gender cannot be blank")
    @Size(max = 1, message = "Gender must be a single character")
    @Pattern(regexp = "M|F", message = "Gender must be 'M' or 'F'")
    private String gender;

    @NotNull
    private Long dni;

    public PatientDto() {}

    public PatientDto(String name, String lastname, LocalDate dateOfBirth, String address, Long phone, String gender, Long dni) {
        this.name = name;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dni = dni;
    }
}
