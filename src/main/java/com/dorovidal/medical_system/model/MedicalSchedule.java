package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "medical_schedules")
public class MedicalSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Doctor doctor;

    private LocalDate dateOfAppointment;

    private LocalTime startOfAppointment;

    private LocalTime endOfAppointment;

    private AppointmentStatus status;

    public MedicalSchedule() {}

}
