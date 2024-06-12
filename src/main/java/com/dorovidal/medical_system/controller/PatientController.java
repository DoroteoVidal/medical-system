package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.PatientDto;
import com.dorovidal.medical_system.dto.PatientUserDto;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> save(@RequestBody @Valid PatientDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patientDto));
        } catch (UserFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("save-me")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> saveWithUser(@RequestBody @Valid PatientUserDto patientUserDtoDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.saveWithUser(patientUserDtoDto));
        } catch (UserFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PatientDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.update(id, patientDto));
        } catch (UserNotFoundException | UserFoundException e) {
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
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(patientService.getById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\") " +
            "or hasAnyAuthority(\"" + AuthorityConstant.USER + "\")")
    public ResponseEntity<List<PatientDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAll());
    }
}
