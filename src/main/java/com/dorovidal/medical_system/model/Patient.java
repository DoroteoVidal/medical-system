package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    private LocalDate dateOfBirth;

    private String address;

    private Long phone;

    @Column(length = 1)
    private String gender;

    private Long dni;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<MedicalHistory> medicalHistory = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new LinkedHashSet<>();

    public Patient() {}

}
