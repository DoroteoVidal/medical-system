package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.MedicalScheduleRequestDto;
import com.dorovidal.medical_system.dto.MedicalScheduleResponseDto;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.AppointmentStatus;
import com.dorovidal.medical_system.model.Doctor;
import com.dorovidal.medical_system.model.MedicalSchedule;
import com.dorovidal.medical_system.repository.DoctorRepository;
import com.dorovidal.medical_system.repository.MedicalScheduleRepository;
import com.dorovidal.medical_system.security.PrincipalProvider;
import com.dorovidal.medical_system.service.MedicalScheduleService;
import com.dorovidal.medical_system.util.MedicalScheduleEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalScheduleImpl implements MedicalScheduleService {

    @Autowired
    private MedicalScheduleRepository medicalScheduleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PrincipalProvider principalProvider;

    @Override
    @Transactional
    public MedicalScheduleResponseDto save(MedicalScheduleRequestDto dto) throws Exception {
        Doctor doctor = doctorRepository
                .findByUserEmail(principalProvider.getPrincipal().getName())
                .orElseThrow(() -> new UserNotFoundException("Doctor not found"));

        MedicalSchedule medicalSchedule = MedicalScheduleEntityUtil.toEntity(dto);
        medicalSchedule.setDoctor(doctor);
        medicalSchedule.setStatus(AppointmentStatus.AVAILABLE);

        return MedicalScheduleEntityUtil.toDto(medicalScheduleRepository.save(medicalSchedule));
    }

    @Override
    public MedicalScheduleResponseDto update(Long id, MedicalScheduleRequestDto dto) throws Exception {
        //Checkear que el estado este disponible antes de actualizar
        return null;
    }

    @Override
    public void delete(Long id) throws Exception {
        //Checkear que el estado este disponible antes de eliminar
    }

    @Override
    public MedicalScheduleResponseDto getById(Long id) throws Exception {
        return null;
    }

    @Override
    public List<MedicalScheduleResponseDto> getAll() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalScheduleResponseDto> getAvailableAppointmentsByDoctorId(Long doctorId) {
        return medicalScheduleRepository.findByDoctorIdAndAvailable(doctorId)
                .stream()
                .map(MedicalScheduleEntityUtil::toDto)
                .toList();
    }
}
