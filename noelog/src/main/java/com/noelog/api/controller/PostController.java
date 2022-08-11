package com.noelog.api.controller;

import com.noelog.api.domain.request.PostSearch;
import com.noelog.api.domain.value.PostValue;
import com.noelog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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


    // 조회 API
    // 지난 시간 = 단건 조회 API
    @GetMapping("/posts/{postId}")
    public PostValue.Res.PostResponse get(@PathVariable(name = "postId") Long postId) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostValue.Res.PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

}
