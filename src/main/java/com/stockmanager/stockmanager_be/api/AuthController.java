package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.constant.ApiUriConstants;
import com.stockmanager.stockmanager_be.dto.request.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.response.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.request.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.response.UserResponseDto;
import com.stockmanager.stockmanager_be.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserServiceImpl userServiceImpl;


    public AuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;

    }

    @PostMapping(value = ApiUriConstants.AUTH_SIGN_UP)
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {

        try {
            UserResponseDto userResponseDto = userServiceImpl.registerUser(userRequestDto);
            return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(ApiUriConstants.AUTH_LOGIN)
    public ResponseEntity<?> userLogin(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        try {
            AuthenticationResponseDto authenticationResponseDto = userServiceImpl.authenticateUser(authenticationRequestDto);
            return new ResponseEntity<>(authenticationResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
}
