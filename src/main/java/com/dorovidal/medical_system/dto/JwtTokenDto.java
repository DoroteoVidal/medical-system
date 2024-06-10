package com.dorovidal.medical_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtTokenDto {

    private String token;

    public JwtTokenDto(String token) {
        this.token = token;
    }

    @JsonProperty("token")
    String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }
}
