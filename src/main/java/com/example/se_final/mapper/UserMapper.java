package com.example.se_final.mapper;

import com.example.se_final.dto.UserDto;
import com.example.se_final.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    User toEntity(UserDto dto);
}