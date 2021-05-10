package com.example.demo.services;

import com.example.demo.dto.companydto.CompListResponceDto;
import com.example.demo.dto.companydto.CompanyReqDto;
import com.example.demo.dto.companydto.CompanyResponceDto;
import org.springframework.data.domain.Sort;

public interface CompanyService {
    CompanyResponceDto addToCompany(CompanyReqDto companyReqDto);
    CompListResponceDto getPaginatedCompanyList(int page, int size, Sort.Direction sort, Long id, String search);
}
