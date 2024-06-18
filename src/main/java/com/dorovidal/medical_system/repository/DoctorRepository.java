package com.dorovidal.medical_system.repository;

import com.dorovidal.medical_system.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(
            "SELECT d FROM Doctor d " +
            "WHERE d.id = :doctorId " +
            "AND d.user.enabled = true"
    )
    Optional<Doctor> findByIdAndUserIsEnabled(Long doctorId);

    @Query(
            "SELECT d FROM Doctor d " +
            "WHERE d.dni = :dni " +
            "AND d.user.enabled = true"
    )
    Optional<Doctor> findByDniAndUserIsEnabled(Long dni);

    @Query(
            "SELECT d FROM Doctor d " +
            "WHERE d.user.email = :email " +
            "AND d.user.enabled = true"
    )
    Optional<Doctor> findByUserEmail(String email);

    Optional<Doctor> findByDni(Long dni);

}
