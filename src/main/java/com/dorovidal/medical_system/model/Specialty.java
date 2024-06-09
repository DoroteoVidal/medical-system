package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "specialties")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public Specialty() {}

}
