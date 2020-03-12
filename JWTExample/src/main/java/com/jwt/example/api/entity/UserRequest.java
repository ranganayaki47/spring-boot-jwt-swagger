package com.jwt.example.api.entity;

import lombok.Data;

@Data
public class UserRequest {

    private String userName;
    private String password;
}
