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
import java.time.LocalTime;
import java.util.List;

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
    public AppointmentDto makeAnAppointment(Long medicalScheduleId, Long patientId) throws UserNotFoundException, AppointmentNotFoundException {
        MedicalSchedule medicalSchedule = medicalScheduleRepository.findByIdAndAvailable(medicalScheduleId)
                .orElseThrow(() -> new AppointmentNotFoundException("Medical schedule does not exists or has already been booked"));

        if(LocalTime.now().equals(medicalSchedule.getStartOfAppointment()) && LocalDate.now().isEqual(medicalSchedule.getDateOfAppointment())||
                LocalTime.now().isAfter(medicalSchedule.getStartOfAppointment()) && LocalDate.now().isEqual(medicalSchedule.getDateOfAppointment())) {
            throw new AppointmentNotFoundException("You cannot reserve this appointment");
        }

        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("The patient does not exist"));

        Appointment appointment = AppointmentEntityUtil.createEntity(patient, medicalSchedule);
        medicalSchedule.setStatus(AppointmentStatus.RESERVED);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return AppointmentEntityUtil.toDto(appointmentSaved);
    }

    /**
     * Los turnos solamente se pueden cancelar con un dia de anticipación.
     *
     * @param appointmentId id del turno que se va a cancelar.
     * @throws AppointmentNotFoundException En caso que no se pueda cancelar debido al
     * horario o no exista dicho turno.
     */
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
        appointment.setMedicalSchedule(null);
        medicalSchedule.setStatus(AppointmentStatus.AVAILABLE);
        appointmentRepository.save(appointment);
        medicalScheduleRepository.save(medicalSchedule);
    }

    @Override
    public List<AppointmentDto> getAllByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(AppointmentEntityUtil::toDto)
                .toList();
    }

}
