package com.pjw.tickgettinig.config.properties;

import com.pjw.tickgettinig.common.ErrorCode;
import com.pjw.tickgettinig.common.exceptions.BusinessException;
import com.pjw.tickgettinig.oauth.SnsType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "oauth2.client")
public class OAuth2Properties {

  private final Properties naver;
  private final Properties google;

  public OAuth2Properties(Properties naver, Properties google) {
    this.naver = naver;
    this.google = google;
  }

  public Properties ofSnsType(SnsType snsType) {
    return switch (snsType) {
      case NAVER -> this.naver;
      case KAKAO -> null; // TODO
      case GOOGLE -> google;
      default -> throw new BusinessException("Invalid SnsType", ErrorCode.INVALID);
    };
  }

  @Getter
  @RequiredArgsConstructor
  public static class Properties {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String tokenUri;
    private final String userInfoUri;
    private final String authorizeUri;

  }
}
