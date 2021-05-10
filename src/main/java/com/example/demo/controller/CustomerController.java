package com.example.demo.controller;

import com.example.demo.dto.customerdto.CustomerListResponse;
import com.example.demo.services.CustomerService;
import com.example.demo.util.AppResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")

public class CustomerController {

    private CustomerService userService;

    public CustomerController(final CustomerService userServices) {
        this.userService = userServices;
    }

    @GetMapping("/usersList")
    public AppResponse<CustomerListResponse> getPaginatedUsersList(
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") final Sort.Direction sort,
            @RequestParam(value = "id", required = false) final Long userId) {

        CustomerListResponse userListResponse = userService.getPaginatedUsersList(page, size, sort, userId);
        if (userListResponse != null) {
            return AppResponse.<CustomerListResponse>builder()
                    .data(userListResponse)
                    .success(true)
                    .message("User List obtained successfully")
                    .build();
        }
        return AppResponse.<CustomerListResponse>builder()
                .success(false)
                .message("No content based on your search")
                .build();
    }

}
