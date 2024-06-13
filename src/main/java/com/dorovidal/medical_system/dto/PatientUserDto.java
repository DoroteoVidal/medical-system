package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientUserDto {

    @NotBlank
    private String address;

    private Long phone;

    @NotBlank(message = "Gender cannot be blank")
    @Size(max = 1, message = "Gender must be a single character")
    @Pattern(regexp = "M|F", message = "Gender must be 'M' or 'F'")
    private String gender;

    @NotNull
    private Long dni;

    public PatientUserDto() {}

    public PatientUserDto(String address, Long phone, String gender, Long dni) {
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dni = dni;
    }
}
