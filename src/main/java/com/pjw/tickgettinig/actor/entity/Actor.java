package com.pjw.tickgettinig.actor.entity;

import com.pjw.tickgettinig.actor.vo.ActorView;
import com.pjw.tickgettinig.actor.vo.AddActorReq;
import com.pjw.tickgettinig.entity.BaseInfoEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Actor extends BaseInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;

    public void update(String name, String image){
        this.name = name;
        this.image = image;
        this.updatedBy = "";
        this.updatedAt = ZonedDateTime.now();
    }

    public ActorView toView(){
        return ActorView.builder()
                .id(id)
                .name(name)
                .image(image)
                .build();
    }

    public static Actor from(AddActorReq req){
        return Actor.builder()
                .id(req.getId())
                .name(req.getName())
                .image(req.getImage())
                .build();
    }

    @Builder
    public Actor(Long id, String name, String image, String createdBy, ZonedDateTime createdAt, String updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
