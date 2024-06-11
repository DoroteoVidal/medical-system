package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.PatientDto;
import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.model.Patient;
import com.dorovidal.medical_system.model.User;
import org.springframework.beans.BeanUtils;

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
}
