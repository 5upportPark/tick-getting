package com.pjw.tickgettinig.actor.entity;

import com.pjw.tickgettinig.actor.vo.ActorView;
import com.pjw.tickgettinig.actor.vo.ActorRequest;
import com.pjw.tickgettinig.entity.BaseInfoEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
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
    }

    public ActorView toView(){
        return ActorView.builder()
                .id(id)
                .name(name)
                .image(image)
                .build();
    }

    public static Actor from(ActorRequest.Add req){
        return Actor.builder()
                .name(req.getName())
                .image(req.getImage())
                .build();
    }

    @Builder
    public Actor(Long id, String name, String image, Long createdBy, LocalDateTime createdAt, Long updatedBy, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
