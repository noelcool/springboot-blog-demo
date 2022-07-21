package com.noelog.api.controller;

import com.noelog.api.domain.value.PostValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    // ssr : html rendering
    // spa : javascript <-> api

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostValue.Req.Creation creation) {
        log.info("params = {}, {}", creation.title(), creation.content());
        return Map.of();
    }



}
