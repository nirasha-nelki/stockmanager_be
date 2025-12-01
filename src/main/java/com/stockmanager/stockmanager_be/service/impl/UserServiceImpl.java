package com.stockmanager.stockmanager_be.service.impl;

import com.stockmanager.stockmanager_be.dto.AuthenticationRequestDto;
import com.stockmanager.stockmanager_be.dto.AuthenticationResponseDto;
import com.stockmanager.stockmanager_be.dto.UserRequestDto;
import com.stockmanager.stockmanager_be.dto.UserResponseDto;
import com.stockmanager.stockmanager_be.entity.User;
import com.stockmanager.stockmanager_be.mapper.UserMapper;
import com.stockmanager.stockmanager_be.repo.UserRepo;
import com.stockmanager.stockmanager_be.service.CustomUserDetailsService;
import com.stockmanager.stockmanager_be.service.UserService;
import com.stockmanager.stockmanager_be.util.JWTUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    UserServiceImpl(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CustomUserDetailsServiceImpl customUserDetailsService, JWTUtil jwtUtil) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {

       try {

           Optional<User> tempUser = userRepo.findByUsername(userRequestDto.getUsername());
           if (tempUser.isPresent()) {
               throw new RuntimeException("Username is already in use");
           }
           User user = new User();
           user.setUsername(userRequestDto.getUsername());
           user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

           user = userRepo.save(user);

           return userMapper.toUserResponseDto(user);
       } catch (Exception e) {
           throw new RuntimeException("Error while registering user " + userRequestDto.getUsername());
       }
    }

    @Override
    public AuthenticationResponseDto authenticateUser(AuthenticationRequestDto authenticationRequestDto) {

//        Optional<User> user = userRepo.findByUsername(authenticationRequestDto.getUsername());
//        if (user.isEmpty()) {
//            throw new UsernameNotFoundException("Username not found");
//        }
//
//        System.out.println(authenticationRequestDto.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Loading user details for token creation
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());

        // generate JWT
        final String token = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponseDto(token);
    }


}
