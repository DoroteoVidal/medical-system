package com.dorovidal.medical_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MedicalHistoryDto {

    @NotBlank
    private String medicalHistory;

    public MedicalHistoryDto() {}

    public MedicalHistoryDto(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
