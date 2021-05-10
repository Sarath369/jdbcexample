package com.example.demo.services.impl;

import com.example.demo.dto.customerdetaildto.CusDetailEditReqDto;
import com.example.demo.dto.customerdetaildto.CusDetailReqDto;
import com.example.demo.dto.customerdetaildto.CusDetailResponceDto;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerDetail;
import com.example.demo.exception.GenericException;
import com.example.demo.repository.CustomerDetailRepository;
import com.example.demo.repository.CustomerOptionalRepository;
import com.example.demo.services.CustomerDetailService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerDetailServiceImplementation implements CustomerDetailService {
    private final CustomerOptionalRepository customerOptionalRepository;
    private CustomerDetailRepository customerDetailRepository;

    public CustomerDetailServiceImplementation(final CustomerDetailRepository customerDetailRepositoryIQ, final CustomerOptionalRepository customerOptionalRepositoryIQ) {
        this.customerOptionalRepository = customerOptionalRepositoryIQ;
        this.customerDetailRepository = customerDetailRepositoryIQ;
    }

    @Override
    public CusDetailResponceDto addCustomerDetails(final CusDetailReqDto cusDetailReqDto) {
        CustomerDetail customerDetail = new CustomerDetail();
        Customer customer = new Customer();
        Optional<Customer> customersearch = customerOptionalRepository.findById(cusDetailReqDto.getCustId());
        if (customersearch != null && customersearch.isPresent()) {
            customer.setId(cusDetailReqDto.getCustId());
            customer.setName(customersearch.get().getName());
            customer.setEmail(customersearch.get().getEmail());
            customer.setPassword(customersearch.get().getPassword());
            customer.setHaspassword(customersearch.get().getHaspassword());
            customer.setDate(customersearch.get().getDate());

            Optional<CustomerDetail> custidfinder = customerDetailRepository.findByCustId(customer);
            customerDetail.setCustId(customer);
            if (custidfinder.isPresent()) {
                throw new GenericException("Bad Request");
            }
        } else {
            throw new GenericException("Bad Request");
        }
        customerDetail.setCityName(cusDetailReqDto.getCityName());
        customerDetail.setTownName(cusDetailReqDto.getTownName());
        customerDetail.setAddress1(cusDetailReqDto.getAddress1());
        customerDetail.setAddress2(cusDetailReqDto.getAddress2());
        customerDetail.setPincode(cusDetailReqDto.getPincode());

        CustomerDetail cdx = customerDetailRepository.save(customerDetail);

        CusDetailResponceDto cdrd = new CusDetailResponceDto();
        cdrd.setCdId(customerDetail.getCdId());
        cdrd.setCustId(customerDetail.getCustId());
        cdrd.setCityName(customerDetail.getCityName());
        cdrd.setTownName(customerDetail.getTownName());
        cdrd.setAddress1(customerDetail.getAddress1());
        cdrd.setAddress2(customerDetail.getAddress2());
        cdrd.setPincode(customerDetail.getPincode());
        return cdrd;
    }

    @Override
    public CusDetailResponceDto updateCustomerDetails(final CusDetailEditReqDto cusDetailEditReqDto) {

        CustomerDetail customerDetail = new CustomerDetail();
        Customer customer = new Customer();
        Optional<CustomerDetail> detailsearch = customerDetailRepository.findByCdId(cusDetailEditReqDto.getCdId());
        if (detailsearch.isPresent() && detailsearch != null) {
            customerDetail.setCdId(cusDetailEditReqDto.getCdId());
            Optional<Customer> customersearch = customerOptionalRepository.findById(cusDetailEditReqDto.getCustId());
            if (customersearch != null && customersearch.isPresent()) {
                customer.setId(customersearch.get().getId());
                customer.setName(customersearch.get().getName());
                customer.setEmail(customersearch.get().getEmail());
                customer.setPassword(customersearch.get().getPassword());
                customer.setHaspassword(customersearch.get().getHaspassword());
                customer.setDate(customersearch.get().getDate());
                customerDetail.setCustId(customer);
            } else {
                throw new GenericException("Bad Request");
            }
            customerDetail.setCityName(cusDetailEditReqDto.getCityName());
            customerDetail.setTownName(cusDetailEditReqDto.getTownName());
            customerDetail.setAddress1(cusDetailEditReqDto.getAddress1());
            customerDetail.setAddress2(cusDetailEditReqDto.getAddress2());
            customerDetail.setPincode(cusDetailEditReqDto.getPincode());
            CustomerDetail customerDetail1X = customerDetailRepository.save(customerDetail);
            CusDetailResponceDto cdrd = new CusDetailResponceDto();
            cdrd.setCdId(customerDetail.getCdId());
            cdrd.setCustId(customerDetail.getCustId());
            cdrd.setCityName(customerDetail.getCityName());
            cdrd.setTownName(customerDetail.getTownName());
            cdrd.setAddress1(customerDetail.getAddress1());
            cdrd.setAddress2(customerDetail.getAddress2());
            cdrd.setPincode(customerDetail.getPincode());
            return cdrd;
        } else {
            throw new GenericException("Bad Request");
        }
    }
}
