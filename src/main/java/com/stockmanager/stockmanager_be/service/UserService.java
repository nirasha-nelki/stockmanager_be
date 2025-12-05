package com.stockmanager.stockmanager_be.service;

import com.stockmanager.stockmanager_be.dto.request.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.response.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.request.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
import com.stockmanager.stockmanager_be.dto.response.UserResponseDto;

public interface UserService {

    ResponseEntityDto registerUser(UserRequestDto userRequestDto);

    ResponseEntityDto authenticateUser(AuthenticationRequestDto authenticationRequestDto);

}
