package org.example.userservice.presentation.request;

import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    private String username;
    private String password;
    private String role;
}
