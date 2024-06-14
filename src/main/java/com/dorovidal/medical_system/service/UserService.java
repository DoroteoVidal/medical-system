package com.dorovidal.medical_system.service;

import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService extends BaseService<UserResponseDto, UserRequestDto> {

    User loadUserByEmail(String email) throws UsernameNotFoundException;
}
