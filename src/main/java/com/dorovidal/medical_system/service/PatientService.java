package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.PatientRequestDto;
import com.dorovidal.medical_system.dto.PatientResponseDto;
import com.dorovidal.medical_system.dto.PatientUserDto;
import com.dorovidal.medical_system.exception.UserFoundException;

public interface PatientService extends BaseService<PatientResponseDto, PatientRequestDto> {

    PatientResponseDto saveWithUser(PatientUserDto patientUserDto) throws UserFoundException;

}
