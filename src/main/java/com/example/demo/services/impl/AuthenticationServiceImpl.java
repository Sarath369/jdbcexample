package com.example.demo.services.impl;


import com.example.demo.dto.StatusDTO;
import com.example.demo.dto.customerdto.AuthResponseDto;
import com.example.demo.dto.customerdto.CustomerLoginReqDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.GenericException;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.CustomerService;
import com.example.demo.services.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * @param customerServiceParam
     * @param authenticationManagerParam
     * @param jwtTokenUtilParam
     * @param passwordEncoderParam
     * @param emailServices1
     */
    @Autowired
    public AuthenticationServiceImpl(final EmailService emailServices1,
                                     final CustomerService customerServiceParam,
                                     final AuthenticationManager authenticationManagerParam,
                                     final JwtTokenUtil jwtTokenUtilParam,
                                     final PasswordEncoder passwordEncoderParam) {
        this.customerService = customerServiceParam;
        this.authenticationManager = authenticationManagerParam;
        this.jwtTokenUtil = jwtTokenUtilParam;
        this.passwordEncoder = passwordEncoderParam;
        this.emailService = emailServices1;
    }


    /**
     * For loginCheck.
     *
     * @param authRequestDTO authRequestDTO
     * @return AuthResponseDTO
     */
    @Override
    public AuthResponseDto loginCheck(final CustomerLoginReqDto authRequestDTO) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final Customer customer = customerService.findUserByEmailAddress(authRequestDTO.getEmail());
        if (customer != null) {
            final String token = jwtTokenUtil.generateToken(customer, authRequestDTO.isHaspassword());
            customer.setDate(LocalDateTime.now());
            customerService.saveUser(customer);
            return buildAuthResponseDTO(customer, token);
        }
        return null;
    }
/*
    /**
     * forgotPassword service.
     *
     * @param email email
     * @return email
     *
     /*
    @Override
    public StatusDTO forgotPassword(final String email) {
        Customer customer = customerService.findUserByEmailAddress(email);
        if (customer == null) {
            throw new GenericException("Invalid email, can't find any user associated with given email!");
        }
        String randomPassword = RandomStringUtils.randomAlphanumeric(10);
        customer.setPassword(passwordEncoder.encode(randomPassword));
        Customer customerToDb = customerService.saveUser(customer);
        if (customerToDb != null) {
            try {
                emailService.sendTempPassword(randomPassword, user.getSalesPerson(), user.getEmail());
            } catch (MessagingException e) {
                throw new BadDataException(e.getLocalizedMessage());
            }
            StatusDTO response = new StatusDTO();
            response.setStatus("OK");
            return response;
        }

        throw new BadDataException("Issue with resetting password, please try again later!");
    }

*/
@Override
public StatusDTO forgotPassword(final String email) {
    Customer customer = customerService.findUserByEmailAddress(email);
    if (customer == null) {
        throw new GenericException("Invalid email, can't find any user associated with given email!");
    }
    String randomPassword = RandomStringUtils.randomAlphanumeric(10);
    customer.setPassword(passwordEncoder.encode(randomPassword));
    Customer userToDb = customerService.saveUser(customer);
    if (userToDb != null) {
        try {
            emailService.sendTempPassword(randomPassword, customer.getName(), customer.getEmail());
        } catch (MessagingException e) {
            throw new GenericException(e.getLocalizedMessage());
        }
        StatusDTO response = new StatusDTO();
        response.setStatus("OK");
        return response;
    }

    throw new GenericException("Issue with sending mail, please try again later!");
}

    /**
     * @param customer
     * @param token
     * @return AutAuthResponseDTO
     */
    private AuthResponseDto buildAuthResponseDTO(final Customer customer, final String token) {
        AuthResponseDto response = new AuthResponseDto();
        response.setId(customer.getId());
        response.setEmail(customer.getEmail());
        response.setToken(token);
        return response;
    }
}
