package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.PatientRequestDto;
import com.dorovidal.medical_system.dto.PatientResponseDto;
import com.dorovidal.medical_system.dto.PatientUserDto;
import com.dorovidal.medical_system.model.Patient;
import com.dorovidal.medical_system.model.User;
import org.springframework.beans.BeanUtils;

public class PatientEntityUtil {

    public static PatientResponseDto toDto(Patient patient) {
        PatientResponseDto patientDto = new PatientResponseDto();
        BeanUtils.copyProperties(patient, patientDto);

        return patientDto;
    }

    public static Patient toEntity(PatientRequestDto patientDto) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDto, patient);

        return patient;
    }

    public static Patient toEntityWithUser(PatientUserDto patientUserDto, User user) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientUserDto, patient);
        patient.setName(user.getName());
        patient.setLastname(user.getLastname());
        patient.setDateOfBirth(user.getDateOfBirth());

        return patient;
    }

    public static void copyProperties(PatientRequestDto patientDto, Patient patient) {
        patient.setName(patientDto.getName());
        patient.setLastname(patientDto.getLastname());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setAddress(patientDto.getAddress());
        patient.setPhone(patientDto.getPhone());
        patient.setGender(patientDto.getGender());
        patient.setDni(patientDto.getDni());
    }
}
