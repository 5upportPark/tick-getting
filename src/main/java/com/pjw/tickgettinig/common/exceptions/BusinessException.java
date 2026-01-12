package com.pjw.tickgettinig.common.exceptions;

import com.pjw.tickgettinig.common.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private ErrorCode errorCode;

  public BusinessException(Exception e) {
    super(e);
    this.errorCode = ErrorCode.INVALID;
  }

  public BusinessException(ErrorCode errorCode, Exception e) {
    super(errorCode.getMessage(), e);
    this.errorCode = errorCode;
  }

  public BusinessException(String message, Exception e) {
    super(message, e);
  }

  public BusinessException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

}
