package com.pjw.tickgettinig.oauth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class OAuthResponse {

  @Getter
  @Setter
  @Schema(description = "네이버 로그인 요청 정보", name = "NaverLoginReq")
  public static class Naver {

    @Schema(description = "토큰 타입 (Bearer)")
    private String token_type;
    @Schema(description = "네이버 액세스 토큰")
    private String access_token;
    @Schema(description = "네이버 갱신 토큰")
    private String refresh_token;
    @Schema(description = "액세스 토큰 만료일 (타임스태프)")
    private Integer expires_in;
    private String error;
    private String error_description;

    public String getAccessToken() {
      return access_token;
    }
  }
}
