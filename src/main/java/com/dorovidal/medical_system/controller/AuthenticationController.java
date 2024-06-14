package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.AuthDto;
import com.dorovidal.medical_system.dto.JwtTokenDto;
import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.exception.UnderageUserException;
import com.dorovidal.medical_system.exception.UserFoundException;
import com.dorovidal.medical_system.model.User;
import com.dorovidal.medical_system.security.DomainUserDetailsService;
import com.dorovidal.medical_system.security.JwtFilter;
import com.dorovidal.medical_system.security.TokenProvider;
import com.dorovidal.medical_system.service.UserService;
import com.dorovidal.medical_system.util.EntityDtoUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AuthenticationController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private UserService userService;

    @GetMapping("actual-user")
    public UserResponseDto getActualUser(Principal principal) {
        return EntityDtoUtil.toDto(userService.loadUserByEmail(principal.getName()));
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserRequestDto userDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
        } catch (UserFoundException | IllegalArgumentException | UnderageUserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("log-in")
    public ResponseEntity<?> logIn(@RequestBody @Valid AuthDto authDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JwtTokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
