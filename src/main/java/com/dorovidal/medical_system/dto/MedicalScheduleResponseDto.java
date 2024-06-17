package com.dorovidal.medical_system.dto;

import com.dorovidal.medical_system.model.AppointmentStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class MedicalScheduleResponseDto {

    private Long id;
    private Long doctorId;
    private LocalDate dateOfAppointment;
    private LocalTime startOfAppointment;
    private LocalTime endOfAppointment;
    private AppointmentStatus status;
}
