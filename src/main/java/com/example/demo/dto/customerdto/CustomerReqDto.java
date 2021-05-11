package com.example.demo.dto.customerdto;

import com.example.demo.util.Role;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomerReqDto {
    private String name;
    private String email;
    private LocalDateTime date;
    private String password;
    private Role role;
    private Boolean haspassword;
}
