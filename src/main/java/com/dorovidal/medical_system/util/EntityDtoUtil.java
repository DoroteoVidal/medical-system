package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.*;
import com.dorovidal.medical_system.model.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserResponseDto toDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRole(user.getUserRoles().iterator().next().getRole().getType());

        return userDto;
    }

    public static User toEntity(UserRequestDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        return user;
    }

    public static PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(patient, patientDto);

        return patientDto;
    }

    public static Patient toEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDto, patient);

        return patient;
    }

    public static Patient toEntity(PatientUserDto patientUserDto, User user) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientUserDto, patient);
        patient.setName(user.getName());
        patient.setLastname(user.getLastname());
        patient.setDateOfBirth(user.getDateOfBirth());

        return patient;
    }

    public static void copyProperties(PatientDto patientDto, Patient patient) {
        patient.setName(patientDto.getName());
        patient.setLastname(patientDto.getLastname());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setAddress(patientDto.getAddress());
        patient.setPhone(patientDto.getPhone());
        patient.setGender(patientDto.getGender());
        patient.setDni(patientDto.getDni());
    }

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
}
