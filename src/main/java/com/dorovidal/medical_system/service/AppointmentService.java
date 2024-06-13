package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.AppointmentRequestDto;
import com.dorovidal.medical_system.dto.AppointmentResponseDto;
import com.dorovidal.medical_system.exception.UserNotFoundException;

public interface AppointmentService {

    AppointmentResponseDto makeAnAppointment(AppointmentRequestDto requestDto) throws UserNotFoundException;
}
