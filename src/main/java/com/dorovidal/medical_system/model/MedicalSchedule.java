package com.dorovidal.medical_system.model;

import jakarta.persistence.*;
import lombok.Data;

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

    private String dayOfWeek;

    private LocalTime startOfShift;

    private LocalTime endOfShift;

    public MedicalSchedule() {}

}
