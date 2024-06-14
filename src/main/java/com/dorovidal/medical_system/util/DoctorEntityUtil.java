package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.DoctorRequestDto;
import com.dorovidal.medical_system.dto.DoctorResponseDto;
import com.dorovidal.medical_system.model.Doctor;
import org.springframework.beans.BeanUtils;

public class DoctorEntityUtil {

    public static Doctor toEntity(DoctorRequestDto doctorDto) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorDto, doctor);

        return doctor;
    }

    public static DoctorResponseDto toDto(Doctor doctor) {
        DoctorResponseDto doctorDto = new DoctorResponseDto();
        BeanUtils.copyProperties(doctor, doctorDto);
        doctorDto.setName(doctor.getUser().getName());
        doctorDto.setLastname(doctor.getUser().getLastname());
        doctorDto.setEmail(doctor.getUser().getEmail());
        doctorDto.setDateOfBirth(doctor.getUser().getDateOfBirth());

        return doctorDto;
    }
}
