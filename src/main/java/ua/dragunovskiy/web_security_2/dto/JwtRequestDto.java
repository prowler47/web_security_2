package ua.dragunovskiy.web_security_2.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}
