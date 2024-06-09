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

    private String name;

    private String lastname;

    private LocalDate dateOfBirth;

    private String address;

    @OneToOne
    private Specialty specialty;

    private Long phone;

    private String email;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Shift> shifts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<MedicalSchedule> medicalSchedules = new LinkedHashSet<>();

    public Doctor() {}

}
