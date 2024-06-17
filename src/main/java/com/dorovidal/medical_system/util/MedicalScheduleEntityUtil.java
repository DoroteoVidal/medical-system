package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.MedicalScheduleRequestDto;
import com.dorovidal.medical_system.dto.MedicalScheduleResponseDto;
import com.dorovidal.medical_system.model.MedicalSchedule;
import org.springframework.beans.BeanUtils;

public class MedicalScheduleEntityUtil {

    public static MedicalSchedule toEntity(MedicalScheduleRequestDto requestDto) {
        MedicalSchedule medicalSchedule = new MedicalSchedule();
        BeanUtils.copyProperties(requestDto, medicalSchedule);

        return medicalSchedule;
    }

    public static MedicalScheduleResponseDto toDto(MedicalSchedule medicalSchedule) {
        MedicalScheduleResponseDto responseDto = new MedicalScheduleResponseDto();
        BeanUtils.copyProperties(medicalSchedule, responseDto);
        responseDto.setDoctorId(medicalSchedule.getDoctor().getId());
        return responseDto;
    }
}
