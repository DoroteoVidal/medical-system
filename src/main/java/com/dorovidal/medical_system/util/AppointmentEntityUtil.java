package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.*;
import com.dorovidal.medical_system.model.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class AppointmentEntityUtil {

    public static Appointment createEntity(Doctor doctor, Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.RESERVED);

        return appointment;
    }

    public static AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        BeanUtils.copyProperties(appointment, appointmentResponseDto);

        return appointmentResponseDto;
    }

    public static void copyProperties(Doctor doctor, Patient patient, AppointmentRequestDto appointmentDto, Appointment appointment) {
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setSchedule(appointmentDto.getSchedule());
        appointment.setUpdatedAt(LocalDateTime.now());
    }
}
