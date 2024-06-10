package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.UserDto;
import com.dorovidal.medical_system.exception.UnderageUserException;
import com.dorovidal.medical_system.exception.UserDeletedException;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Role;
import com.dorovidal.medical_system.model.User;
import com.dorovidal.medical_system.model.UserRole;
import com.dorovidal.medical_system.repository.RoleRepository;
import com.dorovidal.medical_system.repository.UserRepository;
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
    public UserDto save(UserDto userDto) throws UserFoundException, IllegalArgumentException, UnderageUserException {
        userRepository.findUserByEmailIgnoreCase(userDto.getEmail()).orElseThrow(UserFoundException::new);

        if(Period.between(userDto.getDateOfBirth(), LocalDate.now()).getYears() < ALLOWED_AGE) {
            throw new UnderageUserException();
        }

        User user = EntityDtoUtil.toEntity(userDto);
        Role role = roleRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole(user, role);
        roles.add(userRole);
        user.setUserRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);
        User savedUser = userRepository.save(user);

        return EntityDtoUtil.toDto(savedUser);
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) throws UserNotFoundException, UserDeletedException {
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
