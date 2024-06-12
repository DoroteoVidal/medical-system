package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medical_histories")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicalHistory;
}
