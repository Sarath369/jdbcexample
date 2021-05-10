package com.example.demo.controller;


import com.example.demo.dto.companydto.CompListResponceDto;
import com.example.demo.dto.companydto.CompanyReqDto;
import com.example.demo.dto.companydto.CompanyResponceDto;
import com.example.demo.services.CompanyService;
import com.example.demo.util.AppResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/company_api")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(final CompanyService companyServiceHQ) {
        this.companyService = companyServiceHQ;
    }

    @PostMapping("/add_to_company")
    public AppResponse<CompanyResponceDto> addToCompany(@RequestBody final CompanyReqDto companyReqDto) {

        CompanyResponceDto companyResponceDto = companyService.addToCompany(companyReqDto);
        if (companyResponceDto != null) {
            return AppResponse.<CompanyResponceDto>builder()
                    .data(companyResponceDto)
                    .success(true)
                    .message("employee added to company table successfully")
                    .build();
        }

        return AppResponse.<CompanyResponceDto>builder()
                .success(false)
                .message("error")
                .build();


    }

    @GetMapping("/company_list")
    public AppResponse<CompListResponceDto> getPaginatedCompanyList(@RequestParam(value = "page", required = false, defaultValue = "0") final int page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
                                                       @RequestParam(value = "sort", required = false, defaultValue = "ASC") final Sort.Direction sort,
                                                       @RequestParam(value = "slno", required = false) final Long id,
                                                       @RequestParam(value = "Search", required = false) final String search) {
        CompListResponceDto compListResponceDto = companyService.getPaginatedCompanyList(page, size, sort, id, search);
        if (compListResponceDto != null) {
            return AppResponse.<CompListResponceDto>builder()
                    .data(compListResponceDto)
                    .success(true)
                    .message("employee list obtained successfully")
                    .build();
        }

        return AppResponse.<CompListResponceDto>builder()
                .success(false)
                .message("no content based on your search")
                .build();

    }

}
