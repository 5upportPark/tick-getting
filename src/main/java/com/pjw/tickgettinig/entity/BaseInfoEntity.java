package com.pjw.tickgettinig.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseInfoEntity {

    @Column(updatable = false)
    protected String createdBy;

    @Column(updatable = false)
    protected ZonedDateTime createdAt;

    protected String updatedBy;
    protected ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = ZonedDateTime.now();
    }
}
