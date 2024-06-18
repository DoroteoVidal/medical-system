package com.dorovidal.medical_system.repository;

import com.dorovidal.medical_system.model.MedicalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalScheduleRepository extends JpaRepository<MedicalSchedule, Long> {

    @Query(
            "SELECT m FROM MedicalSchedule m " +
            "WHERE m.doctor.id = :doctorId " +
            "AND m.status = AVAILABLE"
    )
    List<MedicalSchedule> findByDoctorIdAndAvailable(Long doctorId);

    @Query(
            "SELECT m FROM MedicalSchedule m " +
            "WHERE m.id = :id " +
            "AND m.status = AVAILABLE"
    )
    Optional<MedicalSchedule> findByIdAndAvailable(Long id);

    @Query(
            "SELECT m FROM MedicalSchedule m " +
            "WHERE m.dateOfAppointment = :dateOfAppointment " +
            "AND m.startOfAppointment = :startOfAppointment"
    )
    Optional<MedicalSchedule> findByDateTime(LocalDate dateOfAppointment, LocalTime startOfAppointment);

}
