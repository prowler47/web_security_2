package ua.dragunovskiy.web_security_2.dto;

import lombok.Data;

@Data
public class JwtResponseDto {
    public JwtResponseDto(String token) {
        this.token = token;
    }

    private String token;
}
