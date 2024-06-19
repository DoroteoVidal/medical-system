package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "medical_histories")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;

    private String medicalHistory;
    private String doctor;
    private LocalDate dateOfAppointment;
    private LocalTime appointmentSchedule;
}
