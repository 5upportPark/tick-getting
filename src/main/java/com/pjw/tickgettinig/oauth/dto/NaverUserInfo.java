package com.pjw.tickgettinig.oauth.dto;

import com.pjw.tickgettinig.oauth.SnsType;
import java.io.Serializable;
import lombok.Builder;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class NaverUserInfo implements OAuth2UserInfo, Serializable {

  private String resultcode;
  private String message;
  private String access_token;
  private String refresh_token;
  private Response response;

  @Builder
  public NaverUserInfo(String resultcode, String message, String accessToken, String refreshToken, Response response) {
    this.resultcode = resultcode;
    this.message = message;
    this.access_token = accessToken;
    this.refresh_token = refreshToken;
    this.response = response;
  }

  // [GET / POST] https://nid.naver.com/oauth2.0/authorize	URL 리다이렉트	  네이버 로그인 인증 요청
  // [GET / POST]	https://nid.naver.com/oauth2.0/token	json	접근 토큰 발급/갱신/삭제 요청

  @ToString
  @Getter
  @Setter
  public static class Response {

    private String id;
    private String nickname;
    private String name;
    private String email;
    private String gender;
    private String age;
    private String birthday;
    private String profileImage;
    private String birthyear;
    private String mobile;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }

  @Override
  public String getProviderId() {
    return response.getId();
  }

  @Override
  public String getProvider() {
    return SnsType.NAVER.name();
  }

  @Override
  public String getEmail() {
    return response.getEmail();
  }

  @Override
  public String getName() {
    return response.getName();
  }

  @Override
  public String getAccessToken() {
    return access_token;
  }

  @Override
  public String getRefreshToken() {
    return refresh_token;
  }
}
