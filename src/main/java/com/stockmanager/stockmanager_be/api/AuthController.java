package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.dto.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.UserResponseDto;
import com.stockmanager.stockmanager_be.service.CustomUserDetailsService;
import com.stockmanager.stockmanager_be.service.impl.UserServiceImpl;
import com.stockmanager.stockmanager_be.util.JWTUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {

        try {
            UserResponseDto userResponseDto = userServiceImpl.registerUser(userRequestDto);
            return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        try {
            AuthenticationResponseDto authenticationResponseDto = userServiceImpl.authenticateUser(authenticationRequestDto);
            return new ResponseEntity<>(authenticationResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
}
