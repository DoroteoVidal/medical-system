package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.MedicalScheduleRequestDto;
import com.dorovidal.medical_system.dto.MedicalScheduleResponseDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;

import java.util.List;

public interface MedicalScheduleService {

    MedicalScheduleResponseDto save (MedicalScheduleRequestDto dto) throws UserNotFoundException, AppointmentNotFoundException;

    void delete (Long id) throws AppointmentNotFoundException;

    MedicalScheduleResponseDto getById(Long id) throws AppointmentNotFoundException;

    List<MedicalScheduleResponseDto> getAll();

    List<MedicalScheduleResponseDto> getAvailableAppointmentsByDoctorId(Long doctorId);

    void completeMedicalSchedule(Long medicalScheduleId) throws AppointmentNotFoundException;
}
