package com.example.demo.controller;




import com.example.demo.dto.customerdto.CusListResponceDto;
import com.example.demo.dto.customerdto.CustomerResponceDto;
import com.example.demo.dto.customerdto.CustomerReqDto;
import com.example.demo.dto.customerdto.CustomerReqEditDto;
import com.example.demo.dto.customerdto.CustomerResponceEditDto;
import com.example.demo.services.CustomerService;
import com.example.demo.util.AppResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class MainController {

    private CustomerService customerService;

    public MainController(final CustomerService customerServiceHQ) {
        this.customerService = customerServiceHQ;
    }

    @PostMapping("/create")
    public AppResponse<CustomerResponceDto> createCustomer(@RequestBody final CustomerReqDto customerRequest) {

        CustomerResponceDto customerResponceDto = customerService.createCustomer(customerRequest);

        if (customerResponceDto != null) {

            return AppResponse.<CustomerResponceDto>builder()
                    .data(customerResponceDto)
                    .success(true)
                    .message("employee created successfully")
                    .build();
        }
        return AppResponse.<CustomerResponceDto>builder()
                .success(false)
                .message(" failed")
                .build();

    }
    @PostMapping("/edit")
    public AppResponse<CustomerResponceEditDto> editCustomer(@RequestBody final CustomerReqEditDto customerRequestEditDTO) {

        CustomerResponceEditDto customerResponceEditDto = customerService.editCustomer(customerRequestEditDTO);
        if (customerResponceEditDto != null) {
            return AppResponse.<CustomerResponceEditDto>builder()
                    .data(customerResponceEditDto)
                    .success(true)
                    .message("successfully updated")
                    .build();


        }
        return AppResponse.<CustomerResponceEditDto>builder()
                .success(false)
                .message("couldnt update")
                .build();
    }

    @GetMapping("/list")
    public AppResponse<CusListResponceDto> getPaginatedCustomerList(@RequestParam(value = "page", required = false, defaultValue = "0") final int page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
                                                       @RequestParam(value = "sort", required = false, defaultValue = "ASC") final Sort.Direction sort,
                                                       @RequestParam(value = "slno", required = false) final Long id,
                                                       @RequestParam(value = "Search", required = false) final String search) {
        CusListResponceDto customerListResponceDto = customerService.getPaginatedCustomerList(page, size, sort, id, search);
        if (customerListResponceDto != null) {
            return AppResponse.<CusListResponceDto>builder()
                    .data(customerListResponceDto)
                    .success(true)
                    .message("employee list obtained successfully")
                    .build();
        }

            return AppResponse.<CusListResponceDto>builder()
                    .success(false)
                    .message("no content based on your search")
                    .build();
    }
/*
    @PostMapping("/login")
    CustomerResponceDto customerLogin(@RequestBody final CustomerLoginReqDto customerLoginReqDto) {
        CustomerResponceDto customerResponceDto = customerService.customerLogin(customerLoginReqDto);
        return customerResponceDto;
    }
 */


}
