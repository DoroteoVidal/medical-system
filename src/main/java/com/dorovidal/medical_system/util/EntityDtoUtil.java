package com.dorovidal.medical_system.util;

import com.dorovidal.medical_system.dto.UserDto;
import com.dorovidal.medical_system.model.User;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        return user;
    }
}
