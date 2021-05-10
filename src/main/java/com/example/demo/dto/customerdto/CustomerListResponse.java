package com.example.demo.dto.customerdto;

import lombok.Data;

import java.util.List;
@Data
public class CustomerListResponse {

    private int totalPages;

    private int currentPage;

    private List<CustomerListDto> customerLists;
}
