package com.example.demo.controller;

import com.example.demo.dto.StatusDTO;
import com.example.demo.dto.customerdto.AuthResponseDto;
import com.example.demo.dto.customerdto.CustomerLoginReqDto;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.CustomerService;
import com.example.demo.util.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;
import static com.example.demo.util.ValidationProcessor.processBindingResult;

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(final AuthenticationService authenticationServiceParam, final CustomerService customerService1) {
        this.authenticationService = authenticationServiceParam;
    }

    /**
     * TO authenticate.
     *
     * @param authRequestDTO authRequestDTO
     * @param bindingResult  bindingResult
     * @return AuthResponseDTO
     */
    @PostMapping(value = "/authenticate")
    public AppResponse<AuthResponseDto> authenticate(@RequestBody @Valid final CustomerLoginReqDto authRequestDTO,
                                                     final BindingResult bindingResult) {

        List<String> errors = processBindingResult(bindingResult);
        if (errors.size() > 0) {
            return AppResponse.<AuthResponseDto>builder()
                    .message(errors.toString())
                    .success(false)
                    .build();
        }

        AuthResponseDto authResponseDTO = authenticationService.loginCheck(authRequestDTO);
        if (authResponseDTO != null) {
            return AppResponse.<AuthResponseDto>builder()
                    .data(authResponseDTO)
                    .message("Successfully authenticated user.")
                    .success(true)
                    .build();
        }
        return AppResponse.<AuthResponseDto>builder()
                .success(false)
                .message("Authentication error, please check provided email or password!")
                .build();
    }

    /**
     * ForgotPassword.
     *
     * @param email email
     * @return Responce
     */
    @PostMapping(value = "/forgotPassword")
    public AppResponse<StatusDTO> forgotPassword(@RequestParam("email") final String email) {
        StatusDTO response = authenticationService.forgotPassword(email);
        if (response != null) {
            return AppResponse.<StatusDTO>builder()
                    .data(response)
                    .message("Password sent successfully")
                    .success(true)
                    .build();
        }
        return AppResponse.<StatusDTO>builder()
                .success(false)
                .message("Issue with resetting password, please try again later!")
                .build();
    }
}
