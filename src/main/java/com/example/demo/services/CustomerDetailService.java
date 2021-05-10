package com.example.demo.services;

import com.example.demo.dto.customerdetaildto.CusDetailEditReqDto;
import com.example.demo.dto.customerdetaildto.CusDetailReqDto;
import com.example.demo.dto.customerdetaildto.CusDetailResponceDto;

public interface CustomerDetailService {

    CusDetailResponceDto addCustomerDetails(CusDetailReqDto cusDetailReqDto);
    CusDetailResponceDto updateCustomerDetails(CusDetailEditReqDto cusDetailEditReqDto);

}
