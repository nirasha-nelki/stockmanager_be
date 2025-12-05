package com.stockmanager.stockmanager_be.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String username;
    private String password;
}
