package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.PatientDto;
import com.dorovidal.medical_system.dto.ShiftRequestDto;
import com.dorovidal.medical_system.dto.ShiftResponseDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;

import java.util.List;

public interface PatientService {

    PatientDto save(PatientDto patientDto) throws UserFoundException;

    PatientDto update(Long patientId, PatientDto patientDto) throws UserNotFoundException, UserFoundException;

    void delete(Long patientId) throws UserNotFoundException;

    PatientDto getById(Long patientId) throws UserNotFoundException;

    List<PatientDto> getAll();

    ShiftResponseDto makeAnAppointment(ShiftRequestDto shiftRequestDto);
}
