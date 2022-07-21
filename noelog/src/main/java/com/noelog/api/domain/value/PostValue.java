package com.noelog.api.domain.value;

import com.noelog.api.util.DescriptionUtils;
import com.noelog.api.util.ErrorResponseUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

public final class PostValue {

    public final static class Req {

        @Builder
        @Schema(name = "Post Creation")
        public record Creation(
                //@NotBlank null, "" 체크 가능
                @Schema(description = DescriptionUtils.POST_TITLE) @NotBlank(message = ErrorResponseUtils.POST_TITLE) String title,
                @Schema(description = DescriptionUtils.POST_CONTENT) @NotBlank(message = ErrorResponseUtils.POST_CONTENT) String content
        ) {

        }
    }

    public final static class Res {

    }

}
