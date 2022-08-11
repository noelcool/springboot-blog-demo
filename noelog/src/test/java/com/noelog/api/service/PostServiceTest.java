package com.noelog.api.service;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.domain.request.PostSearch;
import com.noelog.api.domain.value.PostValue;
import com.noelog.api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @DisplayName("글 1페이지 조회")
    void test3() {
        List<Post> requestPosts = IntStream
                .range(1, 21)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("content " + i)
                        .build())
                .collect(Collectors.toList());
        //given
        postRepository.saveAll(requestPosts);
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        //when
        List<PostValue.Res.PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("제목 20", posts.get(0).getTitle());
        //assertEquals("content 26", posts.get(4).getContent());
    }
}