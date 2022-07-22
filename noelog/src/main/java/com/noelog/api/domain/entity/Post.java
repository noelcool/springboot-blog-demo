package com.noelog.api.domain.entity;

import com.noelog.api.domain.value.PostValue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob //java 에서는 string
    private String content;

    @Builder
    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public static Post of(PostValue.Req.Creation value) {
        return Post.builder()
                .title(value.title())
                .content(value.content())
                .build();
    }
}
