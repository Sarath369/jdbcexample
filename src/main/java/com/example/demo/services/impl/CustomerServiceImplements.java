package com.example.demo.services.impl;
import com.example.demo.dto.customerdto.CusListResponceDto;
import com.example.demo.dto.customerdto.CustomerListDto;
import com.example.demo.dto.customerdto.CustomerListResponse;
import com.example.demo.dto.customerdto.CustomerReqDto;
import com.example.demo.dto.customerdto.CustomerReqEditDto;
import com.example.demo.dto.customerdto.CustomerResponceDto;
import com.example.demo.dto.customerdto.CustomerResponceEditDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.GenericException;
import com.example.demo.repository.CustomerOptionalRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.services.CustomerService;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;


@Service
public class CustomerServiceImplements implements CustomerService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final CustomerOptionalRepository customerOptionalRepository;
    private final EmailService emailService;

    @Autowired
    public CustomerServiceImplements(final EmailService emailService1, final CustomerRepository customerRepositoryIQ, final CustomerOptionalRepository customerOptionalRepositoryIQ, final PasswordEncoder passwordEncoder1) {
        this.customerRepository = customerRepositoryIQ;
        this.emailService = emailService1;
        this.customerOptionalRepository = customerOptionalRepositoryIQ;
        this.passwordEncoder = passwordEncoder1;
    }

    @Override
    public CustomerResponceDto createCustomer(final CustomerReqDto customerReqDto) {

        Customer customer = new Customer();
        customer.setName(customerReqDto.getName());
        customer.setEmail(customerReqDto.getEmail());
        customer.setDate(customerReqDto.getDate());
        customer.setRole(customerReqDto.getRole());
        customer.setHaspassword(customerReqDto.getHaspassword());
        customer.setPassword(passwordEncoder.encode(customerReqDto.getPassword()));

        Customer reCustomer = customerRepository.save(customer);
        CustomerResponceDto customerResponceDto = new CustomerResponceDto();
        customerResponceDto.setId(reCustomer.getId());
        customerResponceDto.setName(reCustomer.getName());
        customerResponceDto.setEmail(reCustomer.getEmail());
        customerResponceDto.setDate(reCustomer.getDate());
        customerResponceDto.setRole(reCustomer.getRole());
        customerResponceDto.setPassword(reCustomer.getPassword());
        customerResponceDto.setHaspassword(reCustomer.getHaspassword());
        try {
            emailService.sendWelcomeMail(customer.getName(), customer.getEmail());
        } catch (MessagingException e) {
            throw new GenericException(e.getLocalizedMessage());
        }
        return customerResponceDto;
    }

    @Override
    public CustomerResponceEditDto editCustomer(final CustomerReqEditDto customerReqEditDto) {
        CustomerResponceEditDto customerResponceEditDto = new CustomerResponceEditDto();
        Optional<Customer> customer = customerOptionalRepository.findById(customerReqEditDto.getId());
        if (customer.isPresent()) {
            customer.get().setName(customerReqEditDto.getName());
            customer.get().setEmail(customerReqEditDto.getEmail());
            customer.get().setDate(customerReqEditDto.getDate());
            customer.get().setRole(customerReqEditDto.getRole());
            customerOptionalRepository.save(customer.get());

            customerResponceEditDto.setId(customer.get().getId());
            customerResponceEditDto.setName(customer.get().getName());
            customerResponceEditDto.setEmail(customer.get().getEmail());
            customerResponceEditDto.setDate(customer.get().getDate());
            customerResponceEditDto.setRole(customer.get().getRole());
        } else {
            throw new GenericException("id not found");
        }
        return customerResponceEditDto;

    }

    @Override
    public CusListResponceDto getPaginatedCustomerList(final int page, final int size, final Sort.Direction sort, final Long id, final String search) {
        Page<Customer> customers;
        List<CustomerListDto> cusList = new ArrayList<>();
        CusListResponceDto customerListResponceDto = new CusListResponceDto();
        PageRequest pageRequest = PageRequest.of(page, size, sort, "id");
        PageRequest pageRequest1 = PageRequest.of(page, size, sort, "name");


        if (search != null) {
            customers = customerOptionalRepository.findBySearch(search, pageRequest1);
        } else if (id != null) {
            customers = customerOptionalRepository.findById(id, pageRequest);
        } else {
            customers = customerOptionalRepository.findAll(pageRequest);
            //customers =customerOptionalRepository.findAllByIsdeletedFalse(pageRequest);

        }
        if (customers != null && customers.getContent().size() > 0) {
            customers.getContent().forEach(cusObj -> {
                CustomerListDto customerResponce = processingCustomerListDTO(cusObj);
                if (customerResponce != null) {
                    cusList.add(customerResponce);
                }
            });
        }
        if (cusList.size() > 0) {
            customerListResponceDto.setCurrentpage(page);
            customerListResponceDto.setTotalpages(customers.getTotalPages());
            customerListResponceDto.setCustomerList(cusList);
            return customerListResponceDto;
        }
        return null;
    }

/*
    @Override
    public CustomerResponceDto customerLogin(final CustomerLoginReqDto customerLoginReqDto) {
        CustomerResponceDto customerResponceDto = new CustomerResponceDto();
        //**Optional<Customer> customer = customerOptionalRepository.findById(customerLoginReqDto.getId());

        Optional<Customer> customer = customerOptionalRepository.findByEmail(customerLoginReqDto.getEmail());
        //if (name != null){customers = customerRepository.findByEmail(providedPassword ,pageRequest1);}

        String providedPassword = customerLoginReqDto.getPassword();
        String securePassword = null;
        if (customer.isPresent()) {
            securePassword = customer.get().getPassword();
//          securePassword = passwordUtil.generateSecurePassword(customer.get().getPassword(), salt);
//          customerLoginReqDto.setPassword(securePassword);
        }




        String newSecurePassword = passwordUtil.generateSecurePassword(providedPassword, salt);

        if (newSecurePassword.equalsIgnoreCase(securePassword)) {
            customerResponceDto.setId(customer.get().getId());
            customerResponceDto.setName(customer.get().getName());
            customerResponceDto.setEmail(customer.get().getEmail());
            customerResponceDto.setDate(customer.get().getDate());
            customerResponceDto.setPassword(customer.get().getPassword());
            customerResponceDto.setRole(customer.get().getRole());
            customerResponceDto.setIsdeleted(customer.get().getIsdeleted());
            return customerResponceDto;
        } else {
           throw new GenericException("User not found");
        }
    }
    */

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        Customer user = findUserByEmailAddress(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<String> role = new HashSet<>();
        role.add(user.getRoleString(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), role.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
    }

    @Override
    public Customer findUserByEmailAddress(final String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer saveUser(final Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerListResponse getPaginatedUsersList(final int page, final int size, final Sort.Direction sort, final Long id) {
        Page<Customer> user;
        List<CustomerListDto> usersList = new ArrayList<>();
        CustomerListResponse userListResponse = new CustomerListResponse();
        PageRequest pageRequest = PageRequest.of(page, size, sort, "salesPerson");
        if (id != null) {
            user = customerOptionalRepository.findById(id, pageRequest);
        } else {
            user = customerRepository.findAll(pageRequest);
        }
        if (user != null && user.getContent().size() > 0) {
            user.getContent().forEach(userObj -> {
                CustomerListDto userResponse = processingCustomerListDTO(userObj);
                if (userResponse != null) {
                    usersList.add(userResponse);
                }
            });
        }
        if (usersList.size() > 0) {
            userListResponse.setCurrentPage(page);
            userListResponse.setTotalPages(user.getTotalPages());
            userListResponse.setCustomerLists(usersList);
            return userListResponse;
        }
        return null;
    }
    @Override
    public CustomerListDto deleteCustomer(final Long id) {

        CustomerListDto customerListDto = new CustomerListDto();
        Optional<Customer> customer = customerOptionalRepository.findById(id);
        if (customer.isPresent()) {
            customer.get().setIsdeleted(true);
            customerOptionalRepository.save(customer.get());
            customerListDto.setId(id);


        }
        return customerListDto;


    }


    private CustomerListDto processingCustomerListDTO(final Customer customerX) {
        CustomerListDto customerListDto = new CustomerListDto();
        if (customerX != null) {
            customerListDto.setId(customerX.getId());
            customerListDto.setName(customerX.getName());
            customerListDto.setEmail(customerX.getEmail());
            customerListDto.setDate(customerX.getDate());
            customerListDto.setRole(customerX.getRole());
        }
        return customerListDto;

    }

}
