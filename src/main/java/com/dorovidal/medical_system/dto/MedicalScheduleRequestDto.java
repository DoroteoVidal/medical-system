package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class MedicalScheduleRequestDto {

    @NotNull
    private LocalDate dateOfAppointment;

    @NotNull
    private LocalTime startOfAppointment;

    @NotNull
    private LocalTime endOfAppointment;

    public MedicalScheduleRequestDto() {}

    public MedicalScheduleRequestDto(LocalDate dateOfAppointment, LocalTime startOfAppointment, LocalTime endOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
        this.startOfAppointment = startOfAppointment;
        this.endOfAppointment = endOfAppointment;
    }
}
