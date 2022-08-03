package com.noelog.api.service;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.domain.value.PostValue;
import com.noelog.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostValue.Req.Creation value) {
        postRepository.save(Post.of(value));
    }

    public Post getRss(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 id입니다"));
    }

    public PostValue.Res.PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 id입니다"));
        return PostValue.Res.PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostValue.Res.PostResponse> getList() {
//        return postRepository.findAll().stream()
//                .map(post ->
//                        PostValue.Res.PostResponse.builder()
//                                .id(post.getId())
//                                .title(post.getTitle())
//                                .content(post.getContent())
//                                .build())
//                .collect(Collectors.toList());
        return postRepository.findAll().stream()
                .map(post -> new PostValue.Res.PostResponse(post))
                .collect(Collectors.toList());
    }
}
