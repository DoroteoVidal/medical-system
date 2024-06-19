package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.MedicalScheduleRequestDto;
import com.dorovidal.medical_system.dto.MedicalScheduleResponseDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.MedicalScheduleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medical-schedules")
@Slf4j
public class MedicalScheduleController {

    @Autowired
    private MedicalScheduleService medicalScheduleService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<?> save(@RequestBody @Valid MedicalScheduleRequestDto requestDto) {
        try{
            log.info("Saving: {}", requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicalScheduleService.save(requestDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\") " +
            "or hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            log.info("Removing by id: {}", id);
            medicalScheduleService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            log.info("Getting by id: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(medicalScheduleService.getById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\") " +
            "or hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<List<MedicalScheduleResponseDto>> getAll() {
        log.info("Get all...");
        return ResponseEntity.status(HttpStatus.OK).body(medicalScheduleService.getAll());
    }

    @GetMapping("by-doctor/{id}")
    public ResponseEntity<List<MedicalScheduleResponseDto>> getAllByDoctorId(@PathVariable Long id) {
        log.info("Get all by doctor id...");
        return ResponseEntity.status(HttpStatus.OK).body(medicalScheduleService.getAvailableAppointmentsByDoctorId(id));
    }

    @GetMapping("complete/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<?> completeById(@PathVariable Long id) {
        try{
            log.info("Completing medical schedule...");
            medicalScheduleService.completeMedicalSchedule(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AppointmentNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
