package com.pjw.tickgettinig.oauth.client;

import com.pjw.tickgettinig.config.properties.OAuth2Properties;
import com.pjw.tickgettinig.oauth.OAuthResponse;
import com.pjw.tickgettinig.oauth.SnsType;
import com.pjw.tickgettinig.oauth.dto.NaverUserInfo;
import com.pjw.tickgettinig.oauth.dto.OAuth2UserInfo;
import com.pjw.tickgettinig.utils.HttpUtil;
import java.util.HashMap;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestTemplateOAuth2Client implements OAuth2Client {

  private final OAuth2Properties properties;

  public RestTemplateOAuth2Client(OAuth2Properties properties) {
    this.properties = properties;
  }

  @Override
  public OAuth2UserInfo getUserInfo(SnsType snsType, String code) {
    String accessToken = getAccessToken(code);
    return getUserAttributes(accessToken);
  }

  private String getAccessToken(String code) {
    Map<String, String> params = new HashMap<>();
    params.put("grant_type", "authorization_code");
    // TODO: sns별로 자동매핑되도록 수정 필요
    params.put("client_id", properties.getNaver().getClientId());
    params.put("client_secret", properties.getNaver().getClientSecret());
    params.put("redirect_uri", properties.getNaver().getRedirectUri());
    params.put("code", code);

    OAuthResponse.Naver result = HttpUtil.urlConnectionBuilder()
        .url(properties.getNaver().getTokenUri()).method(HttpMethod.POST.name())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString())
        .params(params)
        .exchange(OAuthResponse.Naver.class);

    return result.getAccessToken();
  }

  /**
   * SNS사용자 프로필 조회
   *
   * @param accessToken
   * @return
   */
  private OAuth2UserInfo getUserAttributes(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth("Bearer " + accessToken);

    HttpEntity<?> request = new HttpEntity<>(headers);

    NaverUserInfo result = HttpUtil.urlConnectionBuilder()
        .url(properties.getNaver().getUserInfoUri()).method(HttpMethod.POST.name())
        .header("Authorization", "Bearer " + accessToken)
        .exchange(NaverUserInfo.class);

    System.out.println(result);
    return result;
    //restTemplate.postForObject(properties.getNaver().getUserInfoUri(), request, Map.class);
  }
}
