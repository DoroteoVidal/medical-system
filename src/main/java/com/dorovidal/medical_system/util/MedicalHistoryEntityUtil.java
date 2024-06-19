package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.MedicalHistoryDto;
import com.dorovidal.medical_system.model.Appointment;
import com.dorovidal.medical_system.model.MedicalHistory;

public class MedicalHistoryEntityUtil {

    public static MedicalHistory createEntity(MedicalHistoryDto dto, Appointment appointment) {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setMedicalHistory(dto.getMedicalHistory());
        medicalHistory.setDoctor(appointment.getMedicalSchedule().getDoctor().getUser().getName() + " "
                + appointment.getMedicalSchedule().getDoctor().getUser().getLastname());
        medicalHistory.setAppointmentSchedule(appointment.getAppointmentSchedule());
        medicalHistory.setDateOfAppointment(appointment.getDateOfAppointment());
        medicalHistory.setPatient(appointment.getPatient());

        return medicalHistory;
    }
}
