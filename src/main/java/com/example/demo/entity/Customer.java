package com.example.demo.entity;

import com.example.demo.util.Role;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    private String email;
    private LocalDateTime date;
    private String password;
    private Boolean isdeleted;
    private Boolean haspassword;

    public String getRoleString(final Role rolex) {
        String convertedRole;

        convertedRole = rolex.name();

        return convertedRole;
    }
}

