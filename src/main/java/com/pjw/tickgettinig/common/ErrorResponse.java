package com.pjw.tickgettinig.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private int code;
    private String mwssage;

    public static ErrorResponse from(ResponseCode code){
        return ErrorResponse.builder()
                .code(code.getHttpStatus().value())
                .message(code.getMessage())
                .build();
    }

    @Builder
    public ErrorResponse(int code, String message) {
        this.code = code;
        this.mwssage = message;
    }
}
