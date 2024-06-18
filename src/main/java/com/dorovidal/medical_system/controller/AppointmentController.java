package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.AppointmentDto;
import com.dorovidal.medical_system.dto.DoctorResponseDto;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointments")
@Slf4j
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> makeAnAppointment(@RequestParam(name = "medical-schedule") Long medicalScheduleId,
                                               @RequestParam(name = "patient") Long patientId) {
        try{
            log.info("Saving...");
            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.makeAnAppointment(medicalScheduleId, patientId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        try{
            log.info("Removing by id: {}", id);
            appointmentService.cancelAppointment(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("by-patient/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<List<AppointmentDto>> getAllByPatientId(@PathVariable Long id) {
        log.info("Get all by patient id...");
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAllByPatientId(id));
    }
}
