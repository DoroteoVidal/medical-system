package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.PatientRequestDto;
import com.dorovidal.medical_system.dto.PatientResponseDto;
import com.dorovidal.medical_system.dto.PatientUserDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patients")
@Slf4j
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> save(@RequestBody @Valid PatientRequestDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patientDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("save-me")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> saveWithUser(@RequestBody @Valid PatientUserDto patientUserDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.saveWithUser(patientUserDto));
        } catch (UserFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PatientRequestDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.update(id, patientDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\") " +
            "or hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            patientService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(patientService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\") " +
            "or hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<List<PatientResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAll());
    }
}
