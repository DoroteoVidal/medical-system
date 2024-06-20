package com.dorovidal.medical_system.controller;

import com.dorovidal.medical_system.dto.AuthDto;
import com.dorovidal.medical_system.dto.JwtTokenDto;
import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.security.JwtFilter;
import com.dorovidal.medical_system.security.TokenProvider;
import com.dorovidal.medical_system.service.UserService;
import com.dorovidal.medical_system.util.UserEntityUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthenticationController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private UserService userService;

    @GetMapping("actual-user")
    public UserResponseDto getActualUser(Principal principal) {
        log.info("Get actual user...");
        return UserEntityUtil.toDto(userService.loadUserByEmail(principal.getName()));
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserRequestDto userDto) {
        try {
            log.info("Registering user: {}", userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("log-in")
    public ResponseEntity<?> logIn(@RequestBody @Valid AuthDto authDto) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            final var jwt = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            log.info("Login user...");
            return new ResponseEntity<>(new JwtTokenDto(jwt), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
