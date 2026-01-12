package com.pjw.tickgettinig.common.exceptions;

import com.pjw.tickgettinig.common.ErrorCode;
import lombok.Getter;

@Getter
public class JsonParseException extends BusinessException {
    public JsonParseException(String message) {
        super(message, ErrorCode.INVALID);
    }

    public JsonParseException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public JsonParseException(Exception e) {
        super(e);
    }
}
