package com.example.demo.services;


import com.example.demo.dto.StatusDTO;
import com.example.demo.dto.customerdto.AuthResponseDto;
import com.example.demo.dto.customerdto.CustomerLoginReqDto;

public interface AuthenticationService {


    /**
     * The loginCheck.
     *
     * @param authRequestDTO authRequestDTO
     * @return AuthResponseDTO
     */
    AuthResponseDto loginCheck(CustomerLoginReqDto authRequestDTO);

    /**
     * The forgotPassword service.
     *
     * @param email email
     * @return email
     */
    StatusDTO  forgotPassword(String email);
}
