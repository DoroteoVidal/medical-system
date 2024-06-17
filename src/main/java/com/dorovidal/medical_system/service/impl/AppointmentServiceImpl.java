package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.AppointmentDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.*;
import com.dorovidal.medical_system.repository.AppointmentRepository;
import com.dorovidal.medical_system.repository.DoctorRepository;
import com.dorovidal.medical_system.repository.MedicalScheduleRepository;
import com.dorovidal.medical_system.repository.PatientRepository;
import com.dorovidal.medical_system.service.AppointmentService;
import com.dorovidal.medical_system.util.AppointmentEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalScheduleRepository medicalScheduleRepository;

    @Override
    @Transactional
    public AppointmentDto save(Long medicalScheduleId, Long patientId) throws UserNotFoundException, AppointmentNotFoundException {
        MedicalSchedule medicalSchedule = medicalScheduleRepository.findByIdAndAvailable(medicalScheduleId)
                .orElseThrow(() -> new AppointmentNotFoundException("Medical schedule does not exists"));
        Doctor doctor = doctorRepository
                .findByIdAndUserIsEnabled(medicalSchedule.getDoctor().getId())
                .orElseThrow(() -> new UserNotFoundException("The doctor does not exist"));
        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("The patient does not exist"));

        Appointment appointment = AppointmentEntityUtil.createEntity(doctor, patient, medicalSchedule);
        medicalSchedule.setStatus(AppointmentStatus.RESERVED);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return AppointmentEntityUtil.toDto(appointmentSaved);
    }

    @Override
    @Transactional
    public void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);

        if(LocalDate.now().isEqual(LocalDate.from(appointment.getDateOfAppointment())) ||
                LocalDate.now().isAfter(LocalDate.from(appointment.getDateOfAppointment()))) {
            throw new AppointmentNotFoundException("You cannot cancel your appointment");
        }

        MedicalSchedule medicalSchedule = appointment.getMedicalSchedule();

        appointment.setStatus(AppointmentStatus.CANCELED);
        medicalSchedule.setStatus(AppointmentStatus.AVAILABLE);
        appointmentRepository.save(appointment);
        medicalScheduleRepository.save(medicalSchedule);
    }

}
