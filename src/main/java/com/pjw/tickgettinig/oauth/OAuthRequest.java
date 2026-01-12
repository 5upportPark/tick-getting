package com.pjw.tickgettinig.oauth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class OAuthRequest {
    @Getter
    @Setter
    @Schema(description = "네이버 로그인 요청 정보", name = "NaverLoginReq")
    public static class Naver {
        @Schema(description = "토큰 타입 (Bearer)")
        private String tokenType;
        @Schema(description = "네이버 액세스 토큰")
        private String accessToken;
        @Schema(description = "네이버 갱신 토큰")
        private String refreshToken;
        @Schema(description = "액세스 토큰 만료일 (타임스태프)")
        private Integer expiresIn;
    }
}
