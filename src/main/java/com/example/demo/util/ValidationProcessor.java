package com.example.demo.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ValidationProcessor {

    /**
     * @param bindingResult bindingResult
     *@return list of Strings
     */
    public static List<String> processBindingResult(final BindingResult bindingResult) {
        List<String> errorMessage = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage.add(error.getDefaultMessage());
            }
        }
        return errorMessage;
    }
}
