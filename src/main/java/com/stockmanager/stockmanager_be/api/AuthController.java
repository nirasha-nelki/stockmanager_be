package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.constant.ApiUriConstants;
import com.stockmanager.stockmanager_be.dto.request.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.response.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.request.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
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
        ResponseEntityDto responseEntityDto = userServiceImpl.registerUser(userRequestDto);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.CREATED);
    }

    @PostMapping(ApiUriConstants.AUTH_LOGIN)
    public ResponseEntity<?> userLogin(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        ResponseEntityDto responseEntityDto = userServiceImpl.authenticateUser(authenticationRequestDto);
        return new ResponseEntity<>(responseEntityDto, HttpStatus.OK);


    }
}
