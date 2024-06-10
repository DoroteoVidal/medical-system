package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.exception.UnderageUserException;
import com.dorovidal.medical_system.exception.UserDeletedException;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserResponseDto save(UserRequestDto userDto) throws UserFoundException, IllegalArgumentException, UnderageUserException;

    UserResponseDto update(Long userId, UserRequestDto userDto) throws UserNotFoundException, UserDeletedException;

    void delete(Long userId) throws UserNotFoundException, UserDeletedException;

    List<UserResponseDto> getAll();
}
