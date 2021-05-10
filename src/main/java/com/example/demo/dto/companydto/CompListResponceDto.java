package com.example.demo.dto.companydto;


import lombok.Data;

import java.util.List;

@Data
public class CompListResponceDto {

    private int totalpages;
    private int currentpage;
    private List<CompanyListDto> companyList;
}
