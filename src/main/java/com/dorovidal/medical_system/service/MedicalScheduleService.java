package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.MedicalScheduleRequestDto;
import com.dorovidal.medical_system.dto.MedicalScheduleResponseDto;

import java.util.List;

public interface MedicalScheduleService extends BaseService<MedicalScheduleResponseDto, MedicalScheduleRequestDto> {

    List<MedicalScheduleResponseDto> getAvailableAppointmentsByDoctorId(Long doctorId);
}
