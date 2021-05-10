package com.example.demo.dto.customerdetaildto;


import lombok.Data;

@Data
public class CusDetailReqDto {
    private Long custId;
    private String cityName;
    private String townName;
    private String address1;
    private String address2;
    private int pincode;
}
