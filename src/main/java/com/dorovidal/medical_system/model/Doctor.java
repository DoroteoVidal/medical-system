package com.dorovidal.medical_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

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

    private String specialty;

    private String biography;

    private Long phone;

    @Column(length = 1)
    private String gender;

    private Long dni;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<MedicalSchedule> medicalSchedules = new LinkedHashSet<>();

    public Doctor() {}

}
