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
import com.dorovidal.medical_system.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public AppointmentResponseDto makeAnAppointment(AppointmentRequestDto requestDto) throws UserNotFoundException {
        Doctor doctor = doctorRepository
                .findById(requestDto.getDoctorId())
                .orElseThrow(() -> new UserNotFoundException("The doctor does not exist"));
        Patient patient = patientRepository
                .findById(requestDto.getPatientId())
                .orElseThrow(() -> new UserNotFoundException("The patient does not exist"));

        Appointment appointment = EntityDtoUtil.createEntity(doctor, patient);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return EntityDtoUtil.toDto(appointmentSaved);
    }
}
