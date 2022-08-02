package com.noelog.api.controller;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.domain.value.PostValue;
import com.noelog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // ssr : html rendering
    // spa : javascript <-> api

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostValue.Req.Creation request) {
        // 1. 저장된 데이터 entity -> response로 응답하기
        // 2. 저장한 데이터의 primary_id -> response로 응답하기
        // 3. 응답 필요 없음
        // bad : 서버에서 꼭 이렇게 한다고 fix하지 말고 유연하게 대응하는 것이 좋다
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostValue.Res.PostResponse get(@PathVariable(name = "postId") Long id) {
        PostValue.Res.PostResponse response = postService.get(id);
        return response;
    }

    @GetMapping("/posts/{postId}/rss")
    public Post getRss(@PathVariable(name = "postId") Long id) {
        Post post = postService.getRss(id);
        return post;
    }


}
