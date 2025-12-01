package com.stockmanager.stockmanager_be.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private int userId;
    private String username;
    private String password;
}
