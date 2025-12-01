package com.stockmanager.stockmanager_be.api;

import com.stockmanager.stockmanager_be.dto.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.UserResponseDto;
import com.stockmanager.stockmanager_be.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {
//
//        try {
//            UserResponseDto userResponseDto = userServiceImpl.registerUser(userRequestDto);
//            return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}