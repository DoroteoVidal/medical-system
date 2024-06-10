package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.exception.UnderageUserException;
import com.dorovidal.medical_system.exception.UserDeletedException;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Role;
import com.dorovidal.medical_system.model.User;
import com.dorovidal.medical_system.model.UserRole;
import com.dorovidal.medical_system.repository.RoleRepository;
import com.dorovidal.medical_system.repository.UserRepository;
import com.dorovidal.medical_system.security.AuthorityConstant;
import com.dorovidal.medical_system.service.UserService;
import com.dorovidal.medical_system.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    public static final int ALLOWED_AGE = 18;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto save(UserRequestDto userDto) throws UserFoundException, IllegalArgumentException, UnderageUserException {
        if(userRepository.findUserByEmailIgnoreCase(userDto.getEmail()).isPresent()) {
            throw new UserFoundException();
        }

        if(Period.between(userDto.getDateOfBirth(), LocalDate.now()).getYears() < ALLOWED_AGE) {
            throw new UnderageUserException();
        }

        User user = EntityDtoUtil.toEntity(userDto);
        Set<UserRole> roles = new HashSet<>();
        Role role = new Role(3L, AuthorityConstant.PATIENT);
        UserRole userRole = new UserRole(user, role);
        roles.add(userRole);
        for(UserRole ur : roles) {
            roleRepository.save(ur.getRole());
        }
        user.getUserRoles().addAll(roles);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);

        return EntityDtoUtil.toDto(savedUser);
    }

    @Override
    public UserResponseDto update(Long userId, UserRequestDto userDto) throws UserNotFoundException, UserDeletedException {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.findByIsActive(userId).orElseThrow(UserDeletedException::new);

        User user = EntityDtoUtil.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);

        return EntityDtoUtil.toDto(savedUser);
    }

    @Override
    public void delete(Long userId) throws UserNotFoundException, UserDeletedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.findByIsActive(userId).orElseThrow(UserDeletedException::new);

        user.setActive(false);
    }
}
