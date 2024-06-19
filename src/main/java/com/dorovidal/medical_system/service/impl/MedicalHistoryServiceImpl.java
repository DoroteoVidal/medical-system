package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.MedicalHistoryDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.model.Appointment;
import com.dorovidal.medical_system.model.AppointmentStatus;
import com.dorovidal.medical_system.model.MedicalHistory;
import com.dorovidal.medical_system.repository.AppointmentRepository;
import com.dorovidal.medical_system.repository.MedicalHistoryRepository;
import com.dorovidal.medical_system.service.MedicalHistoryService;
import com.dorovidal.medical_system.util.MedicalHistoryEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public MedicalHistory save(MedicalHistoryDto dto, Long medicalScheduleId) throws AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findByMedicalScheduleId(medicalScheduleId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment does not exists"));

        if(!appointment.getStatus().equals(AppointmentStatus.COMPLETED)) {
            throw new AppointmentNotFoundException("Appointment has not been completed");
        }

        MedicalHistory medicalHistory = MedicalHistoryEntityUtil.createEntity(dto, appointment);

        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistory> getAllByPatientId(Long patientId) {
        return medicalHistoryRepository.findAllByPatientId(patientId);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalHistory getById(Long medicalHistoryId) throws AppointmentNotFoundException {
        return medicalHistoryRepository.findById(medicalHistoryId)
                .orElseThrow(() -> new AppointmentNotFoundException("Medical history does not exists"));
    }
}
