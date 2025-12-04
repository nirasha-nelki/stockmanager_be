package com.stockmanager.stockmanager_be.service;

import com.stockmanager.stockmanager_be.dto.request.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.response.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.request.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(UserRequestDto userRequestDto);

    AuthenticationResponseDto authenticateUser(AuthenticationRequestDto authenticationRequestDto);

}
