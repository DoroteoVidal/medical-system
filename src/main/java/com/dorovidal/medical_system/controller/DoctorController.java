package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.DoctorRequestDto;
import com.dorovidal.medical_system.dto.DoctorResponseDto;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.DoctorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctors")
@Slf4j
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<?> save(@RequestBody @Valid DoctorRequestDto doctorDto) {
        try{
            log.info("Saving: {}", doctorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.save(doctorDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid DoctorRequestDto doctorDto) {
        try{
            log.info("Updating doctor with id: {}", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.update(id, doctorDto));
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
            doctorService.delete(id);
            log.info("Removing doctor with id: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            log.info("Getting doctor with id: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(doctorService.getById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\") " +
            "or hasAnyAuthority(\"" + AuthorityConstant.DOCTOR + "\")")
    public ResponseEntity<List<DoctorResponseDto>> getAll() {
        log.info("Getting doctors");
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getAll());
    }
}
