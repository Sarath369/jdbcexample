package com.example.demo.dto.customerdto;

import lombok.Data;

import java.util.List;
@Data
public class CusListResponceDto {
    private int totalpages;

    private int currentpage;

    private List<CustomerListDto> customerList;
}
