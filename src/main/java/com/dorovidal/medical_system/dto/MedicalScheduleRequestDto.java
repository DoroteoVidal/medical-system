package com.dorovidal.medical_system.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class MedicalScheduleRequestDto {

    private LocalDate dateOfAppointment;
    private LocalTime startOfAppointment;
    private LocalTime endOfAppointment;

    public MedicalScheduleRequestDto() {}

    public MedicalScheduleRequestDto(LocalDate dateOfAppointment, LocalTime startOfAppointment, LocalTime endOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
        this.startOfAppointment = startOfAppointment;
        this.endOfAppointment = endOfAppointment;
    }
}
