package com.pjw.tickgettinig.actor.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateActorReq {
    @NotNull(message = "ID는 필수 입력값입니다.")
    private Long id;
    @NotEmpty(message = "이름은 필수 입력값입니다.")
    private String name;
    private String image;

    public UpdateActorReq(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
