package com.noelog.api.controller;

import com.noelog.api.util.ErrorResponseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("요청시 test 리턴")
    void postController_요청시_test리턴() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.
//                        post("/posts").
//                        contentType(MediaType.APPLICATION_FORM_URLENCODED).
//                        param("title", "title test").
//                        param("content", "content test")).
//                andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("post"))
//                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/posts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\": \"title test\", \"content\": \"content test\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("post"))
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
}