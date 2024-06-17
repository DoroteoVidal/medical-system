package com.dorovidal.medical_system.dto;

import com.dorovidal.medical_system.model.AppointmentStatus;
import com.dorovidal.medical_system.model.Doctor;
import com.dorovidal.medical_system.model.Patient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class AppointmentDto {
    private Doctor doctor;
    private Patient patient;
    private LocalDate dateOfAppointment;
    private LocalTime appointmentSchedule;
    private LocalDateTime createdAt;
    private AppointmentStatus status;
}
