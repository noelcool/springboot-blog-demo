package com.noelog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noelog.api.domain.entity.Post;
import com.noelog.api.repository.PostRepository;
import com.noelog.api.util.ErrorResponseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("posts 요청시 test 리턴")
    void posts요청시_test리턴() throws Exception {
        // given
        Post post = Post.builder()
                .title("title test")
                .content("content test")
                .build();
        String json = objectMapper.writeValueAsString(post);

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/posts")
                            .contentType(APPLICATION_JSON)
                            .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수이다")
    void posts요청시_title값은_필수이다() throws Exception {
        // given
        Post post = Post.builder()
                .content("content test")
                .build();
        String json = objectMapper.writeValueAsString(post);

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.title").value(ErrorResponseUtils.POST_TITLE))
                .andDo(print());
    }


    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다")
    void post_요청시_DB에값이_저장된다() throws Exception {
        // given
        Post post = Post.builder()
                .title("title test")
                .content("content test")
                .build();
        String json = objectMapper.writeValueAsString(post);

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1L, postRepository.count());
        Post post_ = postRepository.findAll().get(0);
        assertEquals("title test", post_.getTitle());
        assertEquals("content test", post_.getContent());
    }

}