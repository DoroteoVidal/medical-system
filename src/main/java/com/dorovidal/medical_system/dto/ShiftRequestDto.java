package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShiftRequestDto {

    @NotNull
    private LocalDateTime schedule;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    public ShiftRequestDto() {}

    public ShiftRequestDto(LocalDateTime schedule, Long patientId, Long doctorId) {
        this.schedule = schedule;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
}
