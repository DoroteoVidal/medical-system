package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.PatientDto;
import com.dorovidal.medical_system.dto.PatientUserDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Patient;
import com.dorovidal.medical_system.model.User;
import com.dorovidal.medical_system.repository.PatientRepository;
import com.dorovidal.medical_system.security.DomainUserDetailsService;
import com.dorovidal.medical_system.service.PatientService;
import com.dorovidal.medical_system.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DomainUserDetailsService domainUserDetailsService;

    @Override
    public PatientDto save(PatientDto patientDto, Principal principal) throws UserFoundException {
        User user = (User) domainUserDetailsService.loadUserByEmail(principal.getName());

        if(patientRepository.findByDni(patientDto.getDni()).isPresent()) {
            throw new UserFoundException("Patient with this dni or email already exists");
        }

        Patient patient = EntityDtoUtil.toEntity(patientDto);
        patient.setUser(user);

        return EntityDtoUtil.toDto(patientRepository.save(patient));
    }

    @Override
    public PatientDto saveWithUser(PatientUserDto patientUserDto, Principal principal) throws UserFoundException {
        User user = (User) domainUserDetailsService.loadUserByEmail(principal.getName());

        if(patientRepository.findByDni(patientUserDto.getDni()).isPresent()) {
            throw new UserFoundException("Patient with this dni or email already exists");
        }

        Patient patient = EntityDtoUtil.toEntity(patientUserDto, user);
        patient.setUser(user);

        return EntityDtoUtil.toDto(patientRepository.save(patient));
    }

    @Override
    public PatientDto update(Long patientId, PatientDto patientDto) throws UserNotFoundException, UserFoundException {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new UserNotFoundException("The patient does not exist"));

        if(!patient.getDni().equals(patientDto.getDni())) {
            if(patientRepository.findByDni(patientDto.getDni()).isPresent()) {
                throw new UserFoundException("Patient with this dni already exists");
            }
        }

        EntityDtoUtil.copyProperties(patientDto, patient);
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

}
