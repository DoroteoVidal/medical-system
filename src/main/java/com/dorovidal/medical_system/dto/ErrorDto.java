package com.dorovidal.medical_system.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorDto {
    private int code;
    private String message;
    private String date;

    public ErrorDto(int code, String message) {
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now().toString();
    }

    public String toJson() {
        try{
            return new ObjectMapper().writeValueAsString(this);
        }catch(RuntimeException | JsonProcessingException e) {
            return String.format("{message: %s}", this.message);
        }
    }
}
