package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.UserDto;
import com.dorovidal.medical_system.exception.UnderageUserException;
import com.dorovidal.medical_system.exception.UserDeletedException;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;

public interface UserService {

    UserDto save(UserDto userDto) throws UserFoundException, IllegalArgumentException, UnderageUserException;

    UserDto update(Long userId, UserDto userDto) throws UserNotFoundException, UserDeletedException;

    void delete(Long userId) throws UserNotFoundException, UserDeletedException;
}
