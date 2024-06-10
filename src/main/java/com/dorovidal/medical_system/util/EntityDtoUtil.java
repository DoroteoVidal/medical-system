package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.UserRequestDto;
import com.dorovidal.medical_system.dto.UserResponseDto;
import com.dorovidal.medical_system.model.User;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static UserResponseDto toDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }

    public static User toEntity(UserRequestDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        return user;
    }
}
