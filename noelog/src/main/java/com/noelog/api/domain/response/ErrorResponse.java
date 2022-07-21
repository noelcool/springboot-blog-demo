package com.noelog.api.domain.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

    private Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

}
