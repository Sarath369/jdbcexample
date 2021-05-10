package com.example.demo.dto.customerdto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private Long id;

    private String email;

    private String token;
}
