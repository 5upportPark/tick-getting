package com.pjw.tickgettinig.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseInfoEntity {
    @CreatedBy
    @Column(updatable = false)
    protected String createdBy;

    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedBy
    protected String updatedBy;
    protected LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
