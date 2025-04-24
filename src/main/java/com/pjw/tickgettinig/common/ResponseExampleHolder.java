package com.pjw.tickgettinig.common;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseExampleHolder {
    private Example example;
    private int code;
    private String name;

    @Builder
    public ResponseExampleHolder(Example example, int code, String name) {
        this.example = example;
        this.code = code;
        this.name = name;
    }
}
