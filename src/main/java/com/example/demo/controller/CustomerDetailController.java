package com.example.demo.controller;
import com.example.demo.dto.customerdetaildto.CusDetailEditReqDto;
import com.example.demo.dto.customerdetaildto.CusDetailReqDto;
import com.example.demo.dto.customerdetaildto.CusDetailResponceDto;
import com.example.demo.services.CustomerDetailService;
import com.example.demo.util.AppResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer_detail_api")
public class CustomerDetailController {
    private CustomerDetailService customerDetailService;

    public CustomerDetailController(final CustomerDetailService customerDetailServiceHQ) {
        this.customerDetailService = customerDetailServiceHQ;
    }

    /**
     * add_details.
     * @param cusDetailReqDto cusDetailReqDto
     * @return CusDetailResponceDto
     */
    @PostMapping("/add_details")
    public AppResponse<CusDetailResponceDto> addCustomerDetails(@RequestBody final CusDetailReqDto cusDetailReqDto) {
        CusDetailResponceDto cusDetailResponceDto = customerDetailService.addCustomerDetails(cusDetailReqDto);
        if (cusDetailResponceDto != null) {
            return AppResponse.<CusDetailResponceDto>builder()
                    .data(cusDetailResponceDto)
                    .success(true)
                    .message("employee list obtained successfully")
                    .build();
        }

        return AppResponse.<CusDetailResponceDto>builder()
                .success(false)
                .message("no content based on your search")
                .build();
    }

    /**
     * update_details.
     * @param cusDetailEditReqDto cusDetailEditReqDto
     * @return CusDetailResponceDto
     */
    @PostMapping("/update_details")
    public AppResponse<CusDetailResponceDto> updateCustomerDetails(@RequestBody final CusDetailEditReqDto cusDetailEditReqDto) {
        CusDetailResponceDto cusDetailResponceDto = customerDetailService.updateCustomerDetails(cusDetailEditReqDto);
    if (cusDetailResponceDto != null) {
        return AppResponse.<CusDetailResponceDto>builder()
                .data(cusDetailResponceDto)
                .success(true)
                .message("successfully updated")
                .build();


    }
    return AppResponse.<CusDetailResponceDto>builder()
            .success(false)
            .message("couldnt update")
            .build();
    }
}
