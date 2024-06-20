package com.dorovidal.medical_system.dto;

import com.dorovidal.medical_system.model.AppointmentStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class AppointmentDto {
    private Long id;
    private String patient;
    private LocalDate dateOfAppointment;
    private LocalTime appointmentSchedule;
    private LocalDateTime createdAt;
    private AppointmentStatus status;
}
