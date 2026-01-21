package com.pjw.tickgettinig.oauth.dto;

import com.pjw.tickgettinig.oauth.SnsType;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {

  private String name;
  private String email;
  private String userId;
  private String picture; // profile picture url
  private String local;
  private String familyName;
  private String givenName;

  private Map<String, Object> attributes;

  @Builder
  public GoogleUserInfo(String name, String email, String userId, String picture, String local, String familyName, String givenName,
      Map<String, Object> attributes) {
    this.name = name;
    this.email = email;
    this.userId = userId;
    this.picture = picture;
    this.local = local;
    this.familyName = familyName;
    this.givenName = givenName;
    this.attributes = attributes;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getProviderId() {
    return userId;
  }

  @Override
  public String getProvider() {
    return SnsType.GOOGLE.name();
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getAccessToken() {
    return "";
  }

  @Override
  public String getRefreshToken() {
    return "";
  }
}
