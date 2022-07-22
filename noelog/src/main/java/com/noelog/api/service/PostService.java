package com.noelog.api.service;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.domain.value.PostValue;
import com.noelog.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostValue.Req.Creation value) {
        postRepository.save(Post.of(value));
    }
}
