package com.example.security.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.security.dto.user.UserResponse;
import com.example.security.entities.User;

@Mapper
public interface UserMapper {
    @Mapping(source = "birthday", target = "birthday", qualifiedByName = "formatBirthdayToString")
    UserResponse userToResponse(User user);

    @Mapping(source = "birthday", target = "birthday", qualifiedByName = "parseBirthday")
    void updateUserFromDto(UserResponse dto, @MappingTarget User user);

    @Named("formatBirthdayToString")
    default String formatBirthdayToString(LocalDate birthday) {
        return (birthday != null) ? birthday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    @Named("parseBirthday")
    default LocalDate parseBirthday(String birthday) {
        return (birthday != null && !birthday.isEmpty())
                ? LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : null;
    }
}
