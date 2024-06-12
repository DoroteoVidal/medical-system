package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.PatientDto;
import com.dorovidal.medical_system.dto.PatientUserDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;

import java.security.Principal;
import java.util.List;

public interface PatientService {

    PatientDto save(PatientDto patientDto, Principal principal) throws UserFoundException;

    PatientDto saveWithUser(PatientUserDto patientUserDto, Principal principal) throws UserFoundException;

    PatientDto update(Long patientId, PatientDto patientDto) throws UserNotFoundException, UserFoundException;

    void delete(Long patientId) throws UserNotFoundException;

    PatientDto getById(Long patientId) throws UserNotFoundException;

    List<PatientDto> getAll();
}
