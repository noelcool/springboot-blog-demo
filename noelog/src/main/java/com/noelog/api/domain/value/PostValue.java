package com.noelog.api.domain.value;

import com.noelog.api.util.DescriptionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

public final class PostValue {

    public final static class Req {

        @Builder
        @Schema(name = "Post Creation")
        public record Creation(
                //@NotBlank null, "" 체크 가능
                @Schema(description = DescriptionUtils.POST_TITLE) @NotBlank(message = "타이틀을 입력해 주세요") String title,
                @Schema(description = DescriptionUtils.POST_CONTENT) @NotBlank(message = "컨텐츠를 입력해 주세요") String content
        ) {

        }
    }

    public final static class Res {

    }

}
