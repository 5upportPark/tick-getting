package com.pjw.tickgettinig.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Embeddable
@Getter
@NoArgsConstructor
public class BaseInfoEntity {
    protected String createdBy;
    protected ZonedDateTime createdAt;
    protected String updatedBy;
    protected ZonedDateTime updatedAt;
}
