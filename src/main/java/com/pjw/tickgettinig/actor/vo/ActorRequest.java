package com.pjw.tickgettinig.actor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ActorRequest {

    @Getter
    @AllArgsConstructor
    @Builder
    @Schema(description = "배우 추가")
    public static class Add {
        @NotEmpty(message = "이름은 필수 입력값입니다.")
        @Length(min = 2, max = 20)
        @Schema(description = "배우 이름")
        private String name;

        @Schema(description = "배우 사진")
        private String image;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Schema(description = "배우 정보 변경 요청")
    public static class Edit {
        @NotNull(message = "ID는 필수 입력값입니다.")
        private Long id;

        @NotEmpty(message = "이름은 필수 입력값입니다.")
        @Length(min = 2, max = 20)
        @Schema(description = "배우 이름")
        private String name;

        @Schema(description = "배우 사진")
        private String image;

    }
}
