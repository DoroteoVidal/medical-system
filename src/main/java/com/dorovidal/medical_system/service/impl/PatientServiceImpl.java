package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.PatientDto;
import com.dorovidal.medical_system.dto.ShiftRequestDto;
import com.dorovidal.medical_system.dto.ShiftResponseDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Patient;
import com.dorovidal.medical_system.repository.PatientRepository;
import com.dorovidal.medical_system.service.PatientService;
import com.dorovidal.medical_system.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientDto save(PatientDto patientDto) throws UserFoundException {
        if(patientRepository.findByDniOrEmail(patientDto.getDni(), patientDto.getEmail()).isPresent()) {
            throw new UserFoundException("Patient with this dni or email already exists");
        }

        return EntityDtoUtil.toDto(
                patientRepository.save(
                        EntityDtoUtil.toEntity(patientDto)));
    }

    @Override
    public PatientDto update(Long patientId, PatientDto patientDto) throws UserNotFoundException, UserFoundException {
        if(patientRepository.findByDniOrEmail(patientDto.getDni(), patientDto.getEmail()).isPresent()) {
            throw new UserFoundException("Patient with this dni or email already exists");
        }

        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new UserNotFoundException("The patient does not exist"));
        Patient patientSaved = patientRepository.save(patient);

        return EntityDtoUtil.toDto(patientSaved);
    }

    @Override
    public void delete(Long patientId) throws UserNotFoundException {
        if(!patientRepository.existsById(patientId)) {
            throw new UserNotFoundException("The patient does not exist");
        }

        patientRepository.deleteById(patientId);
    }

    @Override
    public PatientDto getById(Long patientId) throws UserNotFoundException {
        return EntityDtoUtil.toDto(patientRepository.findById(patientId).orElseThrow(
                () -> new UserNotFoundException("The patient does not exist")));
    }

    @Override
    public List<PatientDto> getAll() {
        return patientRepository.findAll()
                .stream()
                .map(EntityDtoUtil::toDto)
                .toList();
    }

    @Override
    public ShiftResponseDto makeAnAppointment(ShiftRequestDto shiftRequestDto) {
        return null;
    }
}
