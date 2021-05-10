package com.example.demo.dto.companydto;

import com.example.demo.entity.Customer;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CompanyListDto {

    private Long companyId;
    private Customer custId;
    private String companyName;
    private LocalDateTime createdDate;
    private Boolean isdeleted;
}
