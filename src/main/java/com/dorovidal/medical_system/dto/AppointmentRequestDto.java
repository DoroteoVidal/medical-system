package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppointmentRequestDto {

    @NotNull
    private LocalDateTime schedule;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    public AppointmentRequestDto() {}

    public AppointmentRequestDto(LocalDateTime schedule, Long patientId, Long doctorId) {
        this.schedule = schedule;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
}
