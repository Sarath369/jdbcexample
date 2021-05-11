package com.example.demo.controller;




import com.example.demo.dto.customerdto.CustomerReqDto;
import com.example.demo.dto.customerdto.CustomerReqEditDto;
import com.example.demo.dto.customerdto.CustomerResponceDto;
import com.example.demo.dto.customerdto.CustomerResponceEditDto;
import com.example.demo.dto.customerdto.CusListResponceDto;
import com.example.demo.dto.customerdto.CustomerListDto;
import com.example.demo.dto.customerdto.CustomerListResponse;
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

    /**
     * Create Customer.
     * @param customerRequest customerRequest
     * @return CustomerResponceDto
     */
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

    /**
     * Edit Customer.
     * @param customerRequestEditDTO customerRequestEditDTO
     * @return CustomerResponceEditDto
     */
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

    /**
     * List all customers.
     * @param page page
     * @param size size
     * @param sort sort
     * @param id id
     * @param search search
     * @return CusListResponceDto
     */
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

    /**
     * Delete Customer.
     * @param id id
     * @return CustomerListResponse
     */
    @GetMapping("/delete")

    public AppResponse<CustomerListResponse> deleteCustomer(
            @RequestParam(value = "id to delete", required = false) final Long id) {


        CustomerListDto customerListDto = customerService.deleteCustomer(id);
        if (customerListDto != null) {
            return AppResponse.<CustomerListResponse>builder()
                    .success(true)
                    .message("deleted")
                    .build();
        }
        return AppResponse.<CustomerListResponse>builder()
                .success(false)
                .message("not deleted")
                .build();
    }
}
