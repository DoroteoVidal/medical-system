package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.AppointmentRequestDto;
import com.dorovidal.medical_system.dto.AppointmentResponseDto;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Appointment;
import com.dorovidal.medical_system.model.Doctor;
import com.dorovidal.medical_system.model.Patient;
import com.dorovidal.medical_system.repository.AppointmentRepository;
import com.dorovidal.medical_system.repository.DoctorRepository;
import com.dorovidal.medical_system.repository.PatientRepository;
import com.dorovidal.medical_system.service.AppointmentService;
import com.dorovidal.medical_system.util.AppointmentEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Transactional
    public AppointmentResponseDto save(AppointmentRequestDto appointmentDto) throws UserNotFoundException {
        Doctor doctor = doctorRepository
                .findById(appointmentDto.getDoctorId())
                .orElseThrow(() -> new UserNotFoundException("The doctor does not exist"));
        Patient patient = patientRepository
                .findById(appointmentDto.getPatientId())
                .orElseThrow(() -> new UserNotFoundException("The patient does not exist"));

        Appointment appointment = AppointmentEntityUtil.createEntity(doctor, patient);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return AppointmentEntityUtil.toDto(appointmentSaved);
    }

    @Override
    public AppointmentResponseDto update(Long appointmentId, AppointmentRequestDto appointmentDto) throws Exception {
        return null;
    }

    @Override
    public void delete(Long appointmentId) throws Exception {

    }

    @Override
    public AppointmentResponseDto getById(Long appointmentId) throws Exception {
        return null;
    }

    @Override
    public List<AppointmentResponseDto> getAll() {
        return List.of();
    }
}
