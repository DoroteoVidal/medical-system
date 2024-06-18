package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.AppointmentDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;

import java.util.List;

public interface AppointmentService {

    AppointmentDto makeAnAppointment(Long medicalScheduleId, Long patientId) throws AppointmentNotFoundException, UserNotFoundException;

    void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException;

    List<AppointmentDto> getAllByPatientId(Long patientId);
}
