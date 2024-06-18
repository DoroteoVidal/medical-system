package com.dorovidal.medical_system.repository;

import com.dorovidal.medical_system.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(
            "SELECT a FROM Appointment a " +
            "WHERE a.patient.id = :patientId"
    )
    List<Appointment> findByPatientId(Long patientId);
}
