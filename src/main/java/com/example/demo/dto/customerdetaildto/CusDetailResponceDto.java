package com.example.demo.dto.customerdetaildto;

import com.example.demo.entity.Customer;
import lombok.Data;

@Data
public class CusDetailResponceDto {
    private Long cdId;
    private Customer custId;
    private String cityName;
    private String townName;
    private String address1;
    private String address2;
    private int pincode;
}
