package com.example.demo.dto.customerdto;

import com.example.demo.util.Role;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomerReqEditDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime date;
}
