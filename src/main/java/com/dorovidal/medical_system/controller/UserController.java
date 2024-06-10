package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> all() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UserRequestDto userDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.update(id, userDto));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
