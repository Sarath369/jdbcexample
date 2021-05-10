package com.example.demo.dto.companydto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CompanyReqDto {
    private Long custId;
    private String companyName;
    private LocalDateTime createdDate;
    private Boolean isdeleted;
}
