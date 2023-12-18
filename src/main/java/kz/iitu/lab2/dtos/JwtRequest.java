package kz.iitu.lab2.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}