package com.pjw.tickgettinig.actor.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AddActorReq {
    @NotEmpty(message = "이름은 필수 입력값입니다.")
    private String name;
    private String image;

    public AddActorReq(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
