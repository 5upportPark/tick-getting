package com.pjw.tickgettinig.common;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    HttpStatus getHttpStatus();
    String getMessage();
}
