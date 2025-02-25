package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.PatientRequestDto;
import com.dorovidal.medical_system.dto.PatientResponseDto;
import com.dorovidal.medical_system.dto.PatientUserDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Patient;
import com.dorovidal.medical_system.model.User;
import com.dorovidal.medical_system.repository.PatientRepository;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.security.PrincipalProvider;
import com.dorovidal.medical_system.service.PatientService;
import com.dorovidal.medical_system.service.UserService;
import com.dorovidal.medical_system.util.PatientEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PrincipalProvider principalProvider;

    private User getUserByDni(Long dni) throws UserFoundException {
        User user = userService.loadUserByEmail(principalProvider.getPrincipal().getName());
        if(patientRepository.findByDni(dni).isPresent()) {
            throw new UserFoundException("Patient with this dni already exists");
        }

        return user;
    }

    @Override
    @Transactional
    public PatientResponseDto save(PatientRequestDto patientDto) throws UserFoundException, UserNotFoundException {
        User user = getUserByDni(patientDto.getDni());
        Patient patient = PatientEntityUtil.toEntity(patientDto);
        patient.setUser(user);

        return PatientEntityUtil.toDto(patientRepository.save(patient));
    }

    @Override
    @Transactional
    public PatientResponseDto saveWithUser(PatientUserDto patientUserDto) throws UserFoundException {
        User user = getUserByDni(patientUserDto.getDni());

        Patient patient = PatientEntityUtil.toEntityWithUser(patientUserDto, user);
        patient.setUser(user);

        return PatientEntityUtil.toDto(patientRepository.save(patient));
    }

    @Override
    @Transactional
    public PatientResponseDto update(Long patientId, PatientRequestDto patientDto) throws UserNotFoundException, UserFoundException {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new UserNotFoundException("The patient does not exist"));

        if(!patient.getDni().equals(patientDto.getDni())) {
            if(patientRepository.findByDni(patientDto.getDni()).isPresent()) {
                throw new UserFoundException("Patient with this dni already exists");
            }
        }

        BeanUtils.copyProperties(patientDto, patient);
        Patient updatedPatient = patientRepository.save(patient);

        return PatientEntityUtil.toDto(updatedPatient);
    }

    @Override
    @Transactional
    public void delete(Long patientId) throws UserNotFoundException {
        if(!patientRepository.existsById(patientId)) {
            throw new UserNotFoundException("The patient does not exist");
        }

        patientRepository.deleteById(patientId);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDto getById(Long patientId) throws UserNotFoundException {
        return PatientEntityUtil.toDto(patientRepository.findById(patientId).orElseThrow(
                () -> new UserNotFoundException("The patient does not exist")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDto> getAll() {
        return patientRepository.findAll()
                .stream()
                .map(PatientEntityUtil::toDto)
                .toList();
    }

}
