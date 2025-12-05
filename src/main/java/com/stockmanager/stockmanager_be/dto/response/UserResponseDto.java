package com.stockmanager.stockmanager_be.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {

    private int userId;
    private String username;
    private String password;
}
