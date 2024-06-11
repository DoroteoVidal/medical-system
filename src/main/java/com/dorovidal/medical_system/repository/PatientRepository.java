package com.dorovidal.medical_system.repository;

import com.dorovidal.medical_system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(
            "SELECT p FROM Patient p WHERE p.dni = :dni OR p.email = :email"
    )
    Optional<Patient> findByDniOrEmail(Long dni, String email);

}
