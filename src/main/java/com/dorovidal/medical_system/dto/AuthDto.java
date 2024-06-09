package com.dorovidal.medical_system.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthDto {
    private String email;
    private String password;
}
