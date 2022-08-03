package com.noelog.api.service;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.domain.value.PostValue;
import com.noelog.api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostValue.Req.Creation value = new PostValue.Req.Creation("title test", "content test");

        // when
        postService.write(value);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("title test", post.getTitle());
        assertEquals("content test", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        //given
        Post request = Post.builder()
                .title("title test")
                .content("content test")
                .build();
        postRepository.save(request);

        // client - json 응답에서 샤싣 값 길이를 최대 10글자로 해주세요

        //when
        PostValue.Res.PostResponse post = postService.get(request.getId());

        //then
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        assertEquals("title test", post.getTitle());
        assertEquals("content test", post.getContent());
    }

    @Test
    @DisplayName("글 전체 조회")
    void test3() {

        //given
        Post request1 = Post.builder()
                .title("title test1")
                .content("content test1")
                .build();
        Post request2 = Post.builder()
                .title("title test2")
                .content("content test2")
                .build();
        Post request3 = Post.builder()
                .title("title test3")
                .content("content test3")
                .build();
        postRepository.saveAll(List.of(request1, request2, request3));

        //when
        List<PostValue.Res.PostResponse> response = postService.getList();

        assertEquals(3L, postRepository.count());
    }
}