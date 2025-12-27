package com.stockmanager.stockmanager_be.service.impl;

import com.stockmanager.stockmanager_be.constant.CommonMessageConstant;
import com.stockmanager.stockmanager_be.dto.request.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.response.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.request.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
import com.stockmanager.stockmanager_be.dto.response.UserResponseDto;
import com.stockmanager.stockmanager_be.entity.User;
import com.stockmanager.stockmanager_be.exception.DuplicateEntityException;
import com.stockmanager.stockmanager_be.exception.ValidationException;
import com.stockmanager.stockmanager_be.mapper.UserMapper;
import com.stockmanager.stockmanager_be.repo.UserRepo;
import com.stockmanager.stockmanager_be.service.UserService;
import com.stockmanager.stockmanager_be.type.ResponseStatus;
import com.stockmanager.stockmanager_be.util.JWTUtil;
import com.stockmanager.stockmanager_be.util.MessageUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JWTUtil jwtUtil;
    private final MessageUtil messageUtil;

    UserServiceImpl(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CustomUserDetailsServiceImpl customUserDetailsService, JWTUtil jwtUtil, MessageUtil messageUtil) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.messageUtil = messageUtil;
    }

    @Override
    public ResponseEntityDto registerUser(UserRequestDto userRequestDto) {

        Optional<User> tempUser = userRepo.findByUsername(userRequestDto.getUsername());
           if (tempUser.isPresent()) {
               throw new DuplicateEntityException(CommonMessageConstant.COMMON_ERROR_USER_ALREADY_EXISTS);
           }
           User user = new User();
           user.setUsername(userRequestDto.getUsername());
           user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

           user = userRepo.save(user);
          UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);
          String message = messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_USER_REGISTERED);
          return new ResponseEntityDto(ResponseStatus.SUCCESSFUL, message, userResponseDto.getUserId());
    }

    @Override
    public ResponseEntityDto authenticateUser(AuthenticationRequestDto authenticationRequestDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ValidationException(CommonMessageConstant.COMMON_ERROR_USER_NOT_FOUND);
        }

        // Loading user details for token creation
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());

        // generate JWT
        final String token = jwtUtil.generateToken(userDetails);

       AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(token);
       return new ResponseEntityDto(ResponseStatus.SUCCESSFUL,
               messageUtil.getMessage(CommonMessageConstant.COMMON_SUCCESS_USER_AUTHENTICATED),
               authenticationResponseDto);
    }


}
