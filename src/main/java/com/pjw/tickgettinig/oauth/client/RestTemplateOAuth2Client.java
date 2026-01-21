package com.pjw.tickgettinig.oauth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.pjw.tickgettinig.common.exceptions.BusinessException;
import com.pjw.tickgettinig.config.properties.OAuth2Properties;
import com.pjw.tickgettinig.oauth.OAuthResponse;
import com.pjw.tickgettinig.oauth.SnsType;
import com.pjw.tickgettinig.oauth.dto.GoogleUserInfo;
import com.pjw.tickgettinig.oauth.dto.NaverUserInfo;
import com.pjw.tickgettinig.oauth.dto.OAuth2UserInfo;
import com.pjw.tickgettinig.utils.HttpUtil;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RestTemplateOAuth2Client implements OAuth2Client {

  private final OAuth2Properties oAuth2Properties;
  private final ObjectMapper objectMapper;

  public RestTemplateOAuth2Client(OAuth2Properties oAuth2Properties, ObjectMapper objectMapper) {
    this.oAuth2Properties = oAuth2Properties;
    this.objectMapper = objectMapper;
  }

  @Override
  public OAuth2UserInfo getUserInfo(SnsType snsType, String code) {
    String accessToken = getToken(snsType, code).getAccessToken();
    return getUserAttributes(snsType, accessToken);
  }

  private OAuthResponse.Naver getToken(SnsType snsType, String code) {
    OAuth2Properties.Properties properties = oAuth2Properties.ofSnsType(snsType);
    Map<String, String> params = new HashMap<>();
    params.put("grant_type", "authorization_code");
    params.put("client_id", properties.getClientId());
    params.put("client_secret", properties.getClientSecret());
    params.put("redirect_uri", properties.getRedirectUri());
    params.put("code", code);

    OAuthResponse.Naver result = HttpUtil.urlConnectionBuilder()
        .url(properties.getTokenUri()).method(HttpMethod.POST.name())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString())
        .params(params)
        .exchange(OAuthResponse.Naver.class); //FIXME

    return result;
  }

  /**
   * SNS사용자 프로필 조회
   *
   * @param snsType
   * @param accessToken
   * @return
   */
  private OAuth2UserInfo getUserAttributes(SnsType snsType, String accessToken) {
    OAuth2Properties.Properties properties = oAuth2Properties.ofSnsType(snsType);
    return HttpUtil.urlConnectionBuilder()
        .url(properties.getUserInfoUri()).method(HttpMethod.POST.name())
        .header("Authorization", "Bearer " + accessToken)
        .exchange(NaverUserInfo.class); //FIXME
  }

  public GoogleUserInfo getGoogleUserInfo(String id) {
    GoogleIdTokenVerifier tokenVerifier = new GoogleIdTokenVerifier.Builder(null, null)
        .setAudience(List.of(oAuth2Properties.getGoogle().getClientId()))
        .build();

    try {
      GoogleIdToken idToken = tokenVerifier.verify(id);
      if (idToken != null) {
        GoogleIdToken.Payload payload = idToken.getPayload();
        String userId = payload.getSubject();
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        GoogleUserInfo userInfo = objectMapper.readValue(String.valueOf(payload), GoogleUserInfo.class);
        return userInfo;
      }
    } catch (GeneralSecurityException | IOException e) {
      throw new BusinessException(e);
    }
    return null;
  }
}
