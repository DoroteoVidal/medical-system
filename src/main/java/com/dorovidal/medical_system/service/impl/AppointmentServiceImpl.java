package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.AppointmentRequestDto;
import com.dorovidal.medical_system.dto.AppointmentResponseDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Appointment;
import com.dorovidal.medical_system.model.AppointmentStatus;
import com.dorovidal.medical_system.model.Doctor;
import com.dorovidal.medical_system.model.Patient;
import com.dorovidal.medical_system.repository.AppointmentRepository;
import com.dorovidal.medical_system.repository.DoctorRepository;
import com.dorovidal.medical_system.repository.PatientRepository;
import com.dorovidal.medical_system.service.AppointmentService;
import com.dorovidal.medical_system.util.AppointmentEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
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
        // Chequear que el turno este disponible para la fecha. Para eso se debera chequear la agenda del
        // doctor y sus horarios.
        Doctor doctor = doctorRepository
                .findByIdAndUserIsEnabled(appointmentDto.getDoctorId())
                .orElseThrow(() -> new UserNotFoundException("The doctor does not exist"));
        Patient patient = patientRepository
                .findById(appointmentDto.getPatientId())
                .orElseThrow(() -> new UserNotFoundException("The patient does not exist"));

        Appointment appointment = AppointmentEntityUtil.createEntity(doctor, patient);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return AppointmentEntityUtil.toDto(appointmentSaved);
    }

    @Override
    @Transactional
    public AppointmentResponseDto update(Long appointmentId, AppointmentRequestDto appointmentDto) throws AppointmentNotFoundException, UserNotFoundException {
        // La actualizacion de un turno debe ser minimo un dia de antelacion y la fecha nueva debe
        // ser un dia despues de la pactada.
        // Tambien se debe chequear que el doctor tenga turnos libres el dia y horario que
        // se quiere modificar.
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);

        if(LocalDate.now().isEqual(LocalDate.from(appointment.getSchedule())) ||
                LocalDate.now().isAfter(LocalDate.from(appointment.getSchedule()))) {
            throw new AppointmentNotFoundException("You cannot change your appointment");
        }

        Doctor doctor = doctorRepository
                .findByIdAndUserIsEnabled(appointmentDto.getDoctorId())
                .orElseThrow(() -> new UserNotFoundException("The doctor does not exist"));
        Patient patient = patientRepository
                .findById(appointmentDto.getPatientId())
                .orElseThrow(() -> new UserNotFoundException("The patient does not exist"));

        AppointmentEntityUtil.copyProperties(doctor, patient, appointmentDto, appointment);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return AppointmentEntityUtil.toDto(updatedAppointment);
    }

    @Override
    @Transactional
    public void delete(Long appointmentId) throws AppointmentNotFoundException {
        // La eliminacion del turno consistira en cancelar el turno, es decir
        // Status = CANCELED. Al igual que actualizar, solo se podra cancelar un dia antes.
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);

        if(LocalDate.now().isEqual(LocalDate.from(appointment.getSchedule())) ||
                LocalDate.now().isAfter(LocalDate.from(appointment.getSchedule()))) {
            throw new AppointmentNotFoundException("You cannot change your appointment");
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDto getById(Long appointmentId) throws AppointmentNotFoundException {
        return AppointmentEntityUtil.toDto(appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDto> getAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentEntityUtil::toDto)
                .toList();
    }
}
