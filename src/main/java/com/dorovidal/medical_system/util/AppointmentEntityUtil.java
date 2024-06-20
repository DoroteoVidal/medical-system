package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.*;
import com.dorovidal.medical_system.model.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class AppointmentEntityUtil {

    public static Appointment createEntity(Patient patient, MedicalSchedule medicalSchedule) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setDateOfAppointment(medicalSchedule.getDateOfAppointment());
        appointment.setAppointmentSchedule(medicalSchedule.getStartOfAppointment());
        appointment.setStatus(AppointmentStatus.RESERVED);
        appointment.setMedicalSchedule(medicalSchedule);

        return appointment;
    }

    public static AppointmentDto toDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        BeanUtils.copyProperties(appointment, appointmentDto);
        appointmentDto.setPatient(appointment.getPatient().getName() + " " + appointment.getPatient().getLastname());

        return appointmentDto;
    }

}
