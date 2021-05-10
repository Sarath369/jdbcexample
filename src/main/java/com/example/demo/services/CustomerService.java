package com.example.demo.services;

import com.example.demo.dto.customerdto.CustomerReqDto;
import com.example.demo.dto.customerdto.CustomerReqEditDto;
import com.example.demo.dto.customerdto.CustomerResponceDto;
import com.example.demo.dto.customerdto.CustomerResponceEditDto;
import com.example.demo.dto.customerdto.CusListResponceDto;
import com.example.demo.dto.customerdto.CustomerListDto;
import com.example.demo.dto.customerdto.CustomerListResponse;
import com.example.demo.entity.Customer;
import org.springframework.data.domain.Sort;


public interface CustomerService {
    CustomerResponceDto createCustomer(CustomerReqDto customerReqDto);
//    StatusDTO welcomeMail(String email);
    CustomerResponceEditDto editCustomer(CustomerReqEditDto customerReqEditDto);
    CusListResponceDto getPaginatedCustomerList(int page, int size, Sort.Direction sort, Long id, String search);
//    CustomerResponceDto customerLogin(CustomerLoginReqDto customerLoginReqDto);
    Customer findUserByEmailAddress(String email);
    Customer saveUser(Customer customer);
    CustomerListDto deleteCustomer(Long id);
    CustomerListResponse getPaginatedUsersList(int page, int size, Sort.Direction sort, Long id);
}
