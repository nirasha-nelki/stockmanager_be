package com.stockmanager.stockmanager_be.service;

import com.stockmanager.stockmanager_be.dto.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(UserRequestDto userRequestDto);

    AuthenticationResponseDto authenticateUser(AuthenticationRequestDto authenticationRequestDto);

}
