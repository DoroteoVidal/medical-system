package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            log.info("Get user by id {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<UserResponseDto>> all() {
        log.info("Get all users...");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UserRequestDto userDto) {
        try{
            log.info("Update user by id {}", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.update(id, userDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            log.info("Delete user by id: {}", id);
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
