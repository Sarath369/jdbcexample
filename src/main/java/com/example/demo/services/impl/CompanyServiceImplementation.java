package com.example.demo.services.impl;

import com.example.demo.dto.companydto.CompListResponceDto;
import com.example.demo.dto.companydto.CompanyListDto;
import com.example.demo.dto.companydto.CompanyReqDto;
import com.example.demo.dto.companydto.CompanyResponceDto;
import com.example.demo.entity.Company;
import com.example.demo.entity.Customer;
import com.example.demo.exception.GenericException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.CustomerOptionalRepository;
import com.example.demo.services.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImplementation implements CompanyService {
    private final CustomerOptionalRepository customerOptionalRepository;
    private final CompanyRepository companyRepository;

    public CompanyServiceImplementation(final CustomerOptionalRepository customerOptionalRepositoryIQ, final CompanyRepository companyRepositoryIQ) {
        this.customerOptionalRepository = customerOptionalRepositoryIQ;
        this.companyRepository = companyRepositoryIQ;
    }

    @Override
    public CompanyResponceDto addToCompany(final CompanyReqDto companyReqDto) {

        Company company = new Company();

        company.setCompanyName(companyReqDto.getCompanyName());
        company.setCreatedDate(companyReqDto.getCreatedDate());
//        company.setIsdeleted(companyReqDto.getIsdeleted());
        Customer customer = new Customer();
        //company.setCustId(customer);
        Optional<Customer> customersearch = customerOptionalRepository.findById(companyReqDto.getCustId());
        if (customersearch.isPresent()) {
            customer.setId(customersearch.get().getId());
            customer.setEmail(customersearch.get().getEmail());
            customer.setName(customersearch.get().getName());
            customer.setDate(customersearch.get().getDate());
            customer.setPassword(customersearch.get().getPassword());
            customer.setHaspassword(customersearch.get().getHaspassword());
            company.setCustId(customer);
            Company reCompany = companyRepository.save(company);
        } else {
            throw new GenericException("Bad request");
        }

        CompanyResponceDto companyResponceDto = new CompanyResponceDto();
        companyResponceDto.setCompanyId(company.getCompanyId());
        companyResponceDto.setCompanyName(company.getCompanyName());
        companyResponceDto.setCustId(company.getCustId());
        companyResponceDto.setCreatedDate(company.getCreatedDate());
 //       companyResponceDto.setIsdeleted();

        return companyResponceDto;
    }

    @Override
    public CompListResponceDto getPaginatedCompanyList(final int page, final int size, final Sort.Direction sort, final Long id, final String search) {
        Page<Company> companies;
        List<CompanyListDto> compList = new ArrayList<>();
        CompListResponceDto compListResponceDto = new CompListResponceDto();
        PageRequest pageRequest = PageRequest.of(page, size, sort, "companyId");
        companies = companyRepository.findAll(pageRequest);
        if (companies != null && companies.getContent().size() > 0) {
            companies.getContent().forEach(compObj -> {
                CompanyListDto companyResponce = processingCompanyListDTO(compObj);
                if (companyResponce != null) {
                    compList.add(companyResponce);
                }
            });
        }
        if (compList.size() > 0) {
            compListResponceDto.setCurrentpage(page);
            compListResponceDto.setTotalpages(companies.getTotalPages());
            compListResponceDto.setCompanyList(compList);
            return compListResponceDto;
        }
        return null;
    }

    private CompanyListDto processingCompanyListDTO(final Company companyX) {
        CompanyListDto companyListDto = new CompanyListDto();
        if (companyX != null) {
            companyListDto.setCompanyId(companyX.getCompanyId());
            companyListDto.setCompanyName(companyX.getCompanyName());
            companyListDto.setCustId(companyX.getCustId());
            companyListDto.setCreatedDate(companyX.getCreatedDate());
        }
        return companyListDto;

    }
}
