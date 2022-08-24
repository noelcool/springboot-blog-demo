package com.noelog.api.domain.value;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.util.DescriptionUtils;
import com.noelog.api.util.ErrorResponseUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public final class PostValue {

    public final static class Dto {

        @Getter
        public static class PostEditor {
            private final String title;
            private final String content;

            @Builder
            public PostEditor(String title, String content) {
                this.title = title;
                this.content = content;
            }

        }
    }

    public final static class Req {

        @Builder
        @Schema(name = "Post Creation")
        public record Creation(
                //@NotBlank null, "" 체크 가능
                @Schema(description = DescriptionUtils.POST_TITLE) @NotBlank(message = ErrorResponseUtils.POST_TITLE) String title,
                @Schema(description = DescriptionUtils.POST_CONTENT) @NotBlank(message = ErrorResponseUtils.POST_CONTENT) String content
        ) {

        }

        @Setter
        @Getter
        @ToString
        public static class Edit {

            @NotBlank(message = "타이틀을 입력하세요")
            private String title;

            @NotBlank(message = "콘텐츠를 입력해주세요")
            private String content;

            @Builder
            public Edit(String title, String content) {
                this.title = title;
                this.content = content;
            }
        }
    }

    public final static class Res {

        @Getter
        public static class PostResponse {
            private final Long id;
            private final String title;
            private final String content;

            public PostResponse(Post post) {
                this.id = post.getId();
                this.title = post.getTitle();
                this.content = post.getContent();
            }

            @Builder
            public PostResponse(Long id, String title, String content) {
                this.id = id;
                this.title = title.substring(0, Math.min(title.length(), 10));
                this.content = content;
            }
        }

    }

}
