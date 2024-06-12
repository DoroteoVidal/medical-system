package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @OneToOne
    private Specialty specialty;

    private String biography;

    private Long phone;

    private String genre;

    private Long dni;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<MedicalSchedule> medicalSchedules = new LinkedHashSet<>();

    public Doctor() {}

}
