package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateOfAppointment;

    private LocalTime appointmentSchedule;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;

    @OneToOne
    private MedicalSchedule medicalSchedule;

    private AppointmentStatus status;

    public Appointment() {}

}
