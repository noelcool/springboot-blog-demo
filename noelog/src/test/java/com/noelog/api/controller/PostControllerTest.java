package com.noelog.api.controller;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.repository.PostRepository;
import com.noelog.api.util.ErrorResponseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("요청시 test 리턴")
    void postController_요청시_test리턴() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        post("/posts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\": \"title test\", \"content\": \"content test\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수이다")
    void postController_요청시_title값은필수이다() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"content test\"}")
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
        mockMvc.perform(MockMvcRequestBuilders.
                        post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니당\", \"content\": \"content입니당\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니당", post.getTitle());
        assertEquals("content입니당", post.getContent());
    }

}