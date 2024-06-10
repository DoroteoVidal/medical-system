package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
