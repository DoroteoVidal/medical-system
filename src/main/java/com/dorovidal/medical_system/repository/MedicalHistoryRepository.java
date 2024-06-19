package com.dorovidal.medical_system.repository;

import com.dorovidal.medical_system.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    @Query(
            "SELECT m FROM MedicalHistory m " +
            "WHERE m.patient.id = :patientId"
    )
    List<MedicalHistory> findAllByPatientId(Long patientId);
}
