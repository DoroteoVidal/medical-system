package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.MedicalScheduleRequestDto;
import com.dorovidal.medical_system.dto.MedicalScheduleResponseDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Appointment;
import com.dorovidal.medical_system.model.AppointmentStatus;
import com.dorovidal.medical_system.model.Doctor;
import com.dorovidal.medical_system.model.MedicalSchedule;
import com.dorovidal.medical_system.repository.AppointmentRepository;
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

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public MedicalScheduleResponseDto save(MedicalScheduleRequestDto requestDto) throws UserNotFoundException, AppointmentNotFoundException {
        Doctor doctor = doctorRepository
                .findByUserEmail(principalProvider.getPrincipal().getName())
                .orElseThrow(() -> new UserNotFoundException("Doctor not found"));

        if(medicalScheduleRepository.findByDateTime(requestDto.getDateOfAppointment(),
                requestDto.getStartOfAppointment()).isPresent()) {
            throw new AppointmentNotFoundException("There is already a medical schedule with this day: " +
                    requestDto.getDateOfAppointment() + ", and time: " + requestDto.getStartOfAppointment());
        }

        MedicalSchedule medicalSchedule = MedicalScheduleEntityUtil.toEntity(requestDto);
        medicalSchedule.setDoctor(doctor);
        medicalSchedule.setStatus(AppointmentStatus.AVAILABLE);

        return MedicalScheduleEntityUtil.toDto(medicalScheduleRepository.save(medicalSchedule));
    }

    @Override
    @Transactional
    public void delete(Long medicalScheduleId) throws AppointmentNotFoundException {
        if (medicalScheduleRepository.findByIdAndAvailable(medicalScheduleId).isPresent()) {
            medicalScheduleRepository.deleteById(medicalScheduleId);
        } else {
            throw new AppointmentNotFoundException("This medical schedule does not exist or is not available");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalScheduleResponseDto getById(Long medicalScheduleId) throws AppointmentNotFoundException {
        return MedicalScheduleEntityUtil
                .toDto(medicalScheduleRepository
                        .findById(medicalScheduleId).orElseThrow(
                                () -> new AppointmentNotFoundException("The medical schedule does not exist")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalScheduleResponseDto> getAll() {
        return medicalScheduleRepository.findAll()
                .stream()
                .map(MedicalScheduleEntityUtil::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalScheduleResponseDto> getAvailableAppointmentsByDoctorId(Long doctorId) {
        return medicalScheduleRepository.findByDoctorIdAndAvailable(doctorId)
                .stream()
                .map(MedicalScheduleEntityUtil::toDto)
                .toList();
    }

    @Override
    public void completeMedicalSchedule(Long medicalScheduleId) throws AppointmentNotFoundException {
        MedicalSchedule medicalSchedule = medicalScheduleRepository.findById(medicalScheduleId)
                .orElseThrow(() ->  new AppointmentNotFoundException("The medical schedule does not exist"));

        if(medicalSchedule.getStatus().equals(AppointmentStatus.RESERVED)) {
            Appointment appointment = appointmentRepository.findByMedicalScheduleId(medicalScheduleId)
                    .orElseThrow(() -> new AppointmentNotFoundException("Medical schedule not found"));
            if(appointment.getStatus().equals(AppointmentStatus.RESERVED)) {
                medicalSchedule.setStatus(AppointmentStatus.COMPLETED);
                appointment.setStatus(AppointmentStatus.COMPLETED);
                medicalScheduleRepository.save(medicalSchedule);
                appointmentRepository.save(appointment);
            } else {
                throw new AppointmentNotFoundException("Can't complete appointment");
            }
        } else {
            throw new AppointmentNotFoundException("Can't complete medical schedule");
        }
    }
}
