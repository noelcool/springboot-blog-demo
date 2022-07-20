package com.noelog.api.controller;

import com.noelog.api.domain.value.PostValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    // ssr : html rendering
    // spa : javascript <-> api

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostValue.Req.Creation creation, BindingResult result) {
        //log.info("params = {}, {}", creation.title(), creation.content());
        log.info(result.toString());
        if(result.hasErrors()) {
            List<FieldError> fieldErrorList = result.getFieldErrors();
            FieldError fieldError = fieldErrorList.get(0);
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of();
    }



}
