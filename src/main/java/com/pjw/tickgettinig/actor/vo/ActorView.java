package com.pjw.tickgettinig.actor.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ActorView {
    private Long id;
    private String name;
    private String image;

    public static ActorView from(Long id, String name, String image){
        return ActorView.builder()
                .id(id)
                .name(name)
                .image(image)
                .build();
    }

    @Builder
    public ActorView(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
