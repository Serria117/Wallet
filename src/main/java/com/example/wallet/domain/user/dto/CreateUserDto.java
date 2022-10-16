package com.example.wallet.domain.user.dto;

import lombok.Data;

@Data
public class CreateUserDto
{
    String username;
    String password;
    String email;
}
