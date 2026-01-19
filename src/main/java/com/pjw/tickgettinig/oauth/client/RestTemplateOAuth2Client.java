package com.pjw.tickgettinig.oauth.client;

import com.pjw.tickgettinig.config.properties.OAuth2Properties;
import com.pjw.tickgettinig.oauth.OAuthResponse;
import com.pjw.tickgettinig.oauth.SnsType;
import com.pjw.tickgettinig.oauth.dto.NaverUserInfo;
import com.pjw.tickgettinig.oauth.dto.OAuth2UserInfo;
import com.pjw.tickgettinig.utils.HttpUtil;
import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RestTemplateOAuth2Client implements OAuth2Client {

  private final OAuth2Properties oAuth2Properties;

  public RestTemplateOAuth2Client(OAuth2Properties oAuth2Properties) {
    this.oAuth2Properties = oAuth2Properties;
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
}
