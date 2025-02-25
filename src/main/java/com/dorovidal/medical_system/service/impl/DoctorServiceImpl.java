package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.DoctorRequestDto;
import com.dorovidal.medical_system.dto.DoctorResponseDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Doctor;
import com.dorovidal.medical_system.model.User;
import com.dorovidal.medical_system.repository.DoctorRepository;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.security.PrincipalProvider;
import com.dorovidal.medical_system.service.DoctorService;
import com.dorovidal.medical_system.service.UserService;
import com.dorovidal.medical_system.util.DoctorEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PrincipalProvider principalProvider;

    @Autowired
    private UserService userService;

    private User getUserByDni(Long dni) throws UserFoundException {
        User user = userService.loadUserByEmail(principalProvider.getPrincipal().getName());
        if(doctorRepository.findByDni(dni).isPresent()) {
            throw new UserFoundException("Doctor with this dni already exists");
        }

        return user;
    }

    @Override
    @Transactional
    public DoctorResponseDto save(DoctorRequestDto doctorDto) throws UserFoundException, UserNotFoundException {
        User user = getUserByDni(doctorDto.getDni());
        Doctor doctor = DoctorEntityUtil.toEntity(doctorDto);
        doctor.setUser(user);

        return DoctorEntityUtil.toDto(doctorRepository.save(doctor));
    }

    @Override
    @Transactional
    public DoctorResponseDto update(Long doctorId, DoctorRequestDto doctorDto) throws UserNotFoundException, UserFoundException {
        Doctor doctor = doctorRepository.findByIdAndUserIsEnabled(doctorId).orElseThrow(
                () -> new UserNotFoundException("The doctor does not exist"));

        if(!doctor.getDni().equals(doctorDto.getDni())) {
            if(doctorRepository.findByDniAndUserIsEnabled(doctorDto.getDni()).isPresent()) {
                throw new UserFoundException("Doctor with this dni already exists");
            }
        }

        BeanUtils.copyProperties(doctorDto, doctor);
        Doctor updatedDoctor = doctorRepository.save(doctor);

        return DoctorEntityUtil.toDto(updatedDoctor);
    }

    @Override
    @Transactional
    public void delete(Long doctorId) throws UserNotFoundException {
        if(!doctorRepository.existsById(doctorId)) {
            throw new UserNotFoundException("The doctor does not exist");
        }

        doctorRepository.deleteById(doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponseDto getById(Long doctorId) throws UserNotFoundException {
        return DoctorEntityUtil.toDto(doctorRepository.findByIdAndUserIsEnabled(doctorId).orElseThrow(
                () -> new UserNotFoundException("The doctor does not exist")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> getAll() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorEntityUtil::toDto)
                .toList();
    }
}
