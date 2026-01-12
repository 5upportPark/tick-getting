package com.pjw.tickgettinig.oauth.dto;

import java.util.Map;

public interface OAuth2UserInfo {

  Map<String, Object> getAttributes();

  String getProviderId();

  String getProvider();

  String getEmail();

  String getName();
}
