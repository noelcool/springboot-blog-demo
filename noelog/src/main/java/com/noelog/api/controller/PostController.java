package com.noelog.api.controller;

import com.noelog.api.domain.value.PostValue;
import com.noelog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // ssr : html rendering
    // spa : javascript <-> api

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostValue.Req.Creation request) {
        log.info("params = {}, {}", request.title(), request.content());
        postService.write(request);
        return Map.of();
    }



}
