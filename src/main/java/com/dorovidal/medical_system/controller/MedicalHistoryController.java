package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.MedicalHistoryDto;
import com.dorovidal.medical_system.exception.AppointmentNotFoundException;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.MedicalHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medical-histories")
@Slf4j
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @PostMapping("save-by/medical-schedule/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<?> saveByMedicalScheduleId(@RequestBody MedicalHistoryDto dto, @PathVariable Long id) {
        try{
            log.info("Saving: {}", dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicalHistoryService.save(dto, id));
        } catch (AppointmentNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("by-patient/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> getByPatientId(@PathVariable Long id) {
        try{
            log.info("Get all by patient id...");
            return ResponseEntity.status(HttpStatus.OK).body(medicalHistoryService.getAllByPatientId(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            log.info("Getting by id: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(medicalHistoryService.getById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
