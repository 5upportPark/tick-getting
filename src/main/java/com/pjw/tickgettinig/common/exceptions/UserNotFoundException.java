package com.pjw.tickgettinig.common.exceptions;

import com.pjw.tickgettinig.common.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }

    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}