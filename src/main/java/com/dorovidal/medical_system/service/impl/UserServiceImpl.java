package com.dorovidal.medical_system.service.impl;

import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.exception.UnderageUserException;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.exception.UserNotFoundException;
import com.dorovidal.medical_system.model.Role;
import com.dorovidal.medical_system.model.User;
import com.dorovidal.medical_system.model.UserRole;
import com.dorovidal.medical_system.repository.RoleRepository;
import com.dorovidal.medical_system.repository.UserRepository;
import com.dorovidal.medical_system.service.UserService;
import com.dorovidal.medical_system.util.UserEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    public static final int ALLOWED_AGE = 18;
    public static final Long USER_ID = 2L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto save(UserRequestDto userDto) throws UserFoundException, IllegalArgumentException, UnderageUserException {
        if(userRepository.findUserByEmailIgnoreCase(userDto.getEmail()).isPresent()) {
            throw new UserFoundException();
        }

        if(Period.between(userDto.getDateOfBirth(), LocalDate.now()).getYears() < ALLOWED_AGE) {
            throw new UnderageUserException();
        }

        User user = UserEntityUtil.toEntity(userDto);
        Role role = roleRepository.findById(USER_ID).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole(user, role);
        roles.add(userRole);
        user.setUserRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);

        return UserEntityUtil.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto update(Long userId, UserRequestDto userDto) throws UserNotFoundException, UserFoundException, UnderageUserException {
        User user = userRepository.findByIdAndEnabledTrue(userId).orElseThrow(UserNotFoundException::new);
        if(!user.getEmail().equals(userDto.getEmail())) {
            if(userRepository.findUserByEmailIgnoreCase(userDto.getEmail()).isPresent()) {
                throw new UserFoundException();
            }
        }

        if(Period.between(userDto.getDateOfBirth(), LocalDate.now()).getYears() < ALLOWED_AGE) {
            throw new UnderageUserException();
        }

        BeanUtils.copyProperties(userDto, user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User updatedUser = userRepository.save(user);

        return UserEntityUtil.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long userId) throws UserNotFoundException {
        User user = userRepository.findByIdAndEnabledTrue(userId).orElseThrow(UserNotFoundException::new);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll() {
        return userRepository
                .findAllByEnabledTrue()
                .stream()
                .map(UserEntityUtil::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getById(Long userId) throws UserNotFoundException {
        return UserEntityUtil.toDto(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmailIgnoreCaseAndEnabledTrue(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("There is no user with email " + email)
                );
    }
}
