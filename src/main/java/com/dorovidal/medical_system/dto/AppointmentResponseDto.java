package com.dorovidal.medical_system.dto;

import com.dorovidal.medical_system.model.AppointmentStatus;
import com.dorovidal.medical_system.model.Doctor;
import com.dorovidal.medical_system.model.Patient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class AppointmentResponseDto {
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime schedule;
    private LocalDateTime createdAt;
    private AppointmentStatus status;
}
