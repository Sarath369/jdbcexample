package com.example.demo.dto.customerdto;

import com.example.demo.util.Role;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomerListDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime date;
    private Role role;

}
