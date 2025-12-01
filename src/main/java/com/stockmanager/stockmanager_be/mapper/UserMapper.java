package com.stockmanager.stockmanager_be.mapper;

import com.stockmanager.stockmanager_be.dto.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.UserResponseDto;
import com.stockmanager.stockmanager_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserRequestDto userRequestDto);

    @Mapping(source = "userId", target = "userId")
    UserResponseDto toUserResponseDto(User user);
}
