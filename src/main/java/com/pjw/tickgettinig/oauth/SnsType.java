package com.pjw.tickgettinig.oauth;

public enum SnsType {
  NAVER,
  KAKAO,
  GOOGLE;

  public static SnsType of(String type) {
    return SnsType.valueOf(type.toUpperCase());
  }
}
