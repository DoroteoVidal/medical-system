package com.dorovidal.medical_system.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PatientDto {
    private String name;
    private String lastname;
    private LocalDate dateOfBirth;
    private String address;
    private Long phone;
    private String genre;
    private Long dni;
    private String email;

    public PatientDto() {}

    public PatientDto(String name,
                      String lastname,
                      LocalDate dateOfBirth,
                      String address,
                      Long phone,
                      String genre,
                      Long dni,
                      String email) {
        this.name = name;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.genre = genre;
        this.dni = dni;
        this.email = email;
    }
}
