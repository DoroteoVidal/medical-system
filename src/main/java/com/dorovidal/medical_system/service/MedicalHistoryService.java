package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.MedicalHistoryDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.model.MedicalHistory;

import java.util.List;

public interface MedicalHistoryService {

    MedicalHistory save(MedicalHistoryDto dto, Long medicalScheduleId) throws AppointmentNotFoundException;

    List<MedicalHistory> getAllByPatientId(Long patientId);

    MedicalHistory getById(Long medicalHistoryId) throws AppointmentNotFoundException;
}
