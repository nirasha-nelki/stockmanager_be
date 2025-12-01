package com.stockmanager.stockmanager_be.service.impl;

import com.stockmanager.stockmanager_be.entity.User;
import com.stockmanager.stockmanager_be.repo.UserRepo;
import com.stockmanager.stockmanager_be.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return proper UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // Add authorities here if you have roles
        );

    }
}
